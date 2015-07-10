/*
*************************************************************************
Copyright (c) 2011-2014:
Istituto Nazionale di Fisica Nucleare (INFN), Italy
Consorzio COMETA (COMETA), Italy

See http://www.infn.it and and http://www.consorzio-cometa.it for details on
the copyright holders.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author <a href="mailto:giuseppe.larocca@ct.infn.it">Giuseppe La Rocca</a>
***************************************************************************
*/
package it.infn.ct.trodan;

// import liferay libraries
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;

// import DataEngine libraries
import com.liferay.portal.util.PortalUtil;
import it.infn.ct.GridEngine.InformationSystem.BDII;
import it.infn.ct.GridEngine.Job.*;

// import generic Java libraries
import it.infn.ct.GridEngine.UsersTracking.UsersTrackingDBInterface;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URI;

// import portlet libraries
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

// Importing Apache libraries
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class Trodan extends GenericPortlet {

    private static Log log = LogFactory.getLog(Trodan.class);   

    @Override
    protected void doEdit(RenderRequest request,
            RenderResponse response)
            throws PortletException, IOException
    {

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        response.setContentType("text/html");
        
        // Getting the TRODAN INFRASTRUCTURE from the portlet preferences for the COMETA VO
        String cometa_trodan_INFRASTRUCTURE = portletPreferences.getValue("cometa_trodan_INFRASTRUCTURE", "N/A");
        // Getting the TRODAN VONAME from the portlet preferences for the COMETA VO
        String cometa_trodan_VONAME = portletPreferences.getValue("cometa_trodan_VONAME", "N/A");
        // Getting the TRODAN TOPPBDII from the portlet preferences for the COMETA VO
        String cometa_trodan_TOPBDII = portletPreferences.getValue("cometa_trodan_TOPBDII", "N/A");
        // Getting the TRODAN WMS from the portlet preferences for the COMETA VO
        String[] cometa_trodan_WMS = portletPreferences.getValues("cometa_trodan_WMS", new String[5]);
        // Getting the TRODAN ETOKENSERVER from the portlet preferences for the COMETA VO
        String cometa_trodan_ETOKENSERVER = portletPreferences.getValue("cometa_trodan_ETOKENSERVER", "N/A");
        // Getting the TRODAN MYPROXYSERVER from the portlet preferences for the COMETA VO
        String cometa_trodan_MYPROXYSERVER = portletPreferences.getValue("cometa_trodan_MYPROXYSERVER", "N/A");
        // Getting the TRODAN PORT from the portlet preferences for the COMETA VO
        String cometa_trodan_PORT = portletPreferences.getValue("cometa_trodan_PORT", "N/A");
        // Getting the TRODAN METADATASERVER from the portlet preferences for the COMETA VO
        String cometa_trodan_METADATASERVER = portletPreferences.getValue("cometa_trodan_METADATASERVER", "N/A");
        // Getting the TRODAN METADATASERVER_PORT from the portlet preferences for the COMETA VO
        String cometa_trodan_METADATAPORT = portletPreferences.getValue("cometa_trodan_METADATAPORT", "N/A");        
        // Getting the TRODAN ROBOTID from the portlet preferences for the COMETA VO
        String cometa_trodan_ROBOTID = portletPreferences.getValue("cometa_trodan_ROBOTID", "N/A");
        // Getting the TRODAN ROLE from the portlet preferences for the COMETA VO
        String cometa_trodan_ROLE = portletPreferences.getValue("cometa_trodan_ROLE", "N/A");
        // Getting the TRODAN RENEWAL from the portlet preferences for the COMETA VO
        String cometa_trodan_RENEWAL = portletPreferences.getValue("cometa_trodan_RENEWAL", "checked");
        // Getting the TRODAN DISABLEVOMS from the portlet preferences for the COMETA VO
        String cometa_trodan_DISABLEVOMS = portletPreferences.getValue("cometa_trodan_DISABLEVOMS", "unchecked");

        // Getting the TRODAN INFRASTRUCTURE from the portlet preferences for the GRIDIT VO
        String gridit_trodan_INFRASTRUCTURE = portletPreferences.getValue("gridit_trodan_INFRASTRUCTURE", "N/A");
        // Getting the TRODAN VONAME from the portlet preferences for the GRIDIT VO
        String gridit_trodan_VONAME = portletPreferences.getValue("gridit_trodan_VONAME", "N/A");
        // Getting the TRODAN TOPPBDII from the portlet preferences for the GRIDIT VO
        String gridit_trodan_TOPBDII = portletPreferences.getValue("gridit_trodan_TOPBDII", "N/A");
        // Getting the TRODAN WMS from the portlet preferences for the GRIDIT VO
        String[] gridit_trodan_WMS = portletPreferences.getValues("gridit_trodan_WMS", new String[5]);
        // Getting the TRODAN ETOKENSERVER from the portlet preferences for the GRIDIT VO
        String gridit_trodan_ETOKENSERVER = portletPreferences.getValue("gridit_trodan_ETOKENSERVER", "N/A");
        // Getting the TRODAN MYPROXYSERVER from the portlet preferences for the GRIDIT VO
        String gridit_trodan_MYPROXYSERVER = portletPreferences.getValue("gridit_trodan_MYPROXYSERVER", "N/A");
        // Getting the TRODAN PORT from the portlet preferences for the GRIDIT VO
        String gridit_trodan_PORT = portletPreferences.getValue("gridit_trodan_PORT", "N/A");
        // Getting the TRODAN METADATASERVER from the portlet preferences for the GRIDIT VO
        String gridit_trodan_METADATASERVER = portletPreferences.getValue("gridit_trodan_METADATASERVER", "N/A");
        // Getting the TRODAN METADATASERVER_PORT from the portlet preferences for the GRIDIT VO
        String gridit_trodan_METADATAPORT = portletPreferences.getValue("gridit_trodan_METADATAPORT", "N/A");
        // Getting the TRODAN ROBOTID from the portlet preferences for the GRIDIT VO
        String gridit_trodan_ROBOTID = portletPreferences.getValue("gridit_trodan_ROBOTID", "N/A");
        // Getting the TRODAN ROLE from the portlet preferences for the GRIDIT VO
        String gridit_trodan_ROLE = portletPreferences.getValue("gridit_trodan_ROLE", "N/A");
        // Getting the TRODAN RENEWAL from the portlet preferences for the GRIDIT VO
        String gridit_trodan_RENEWAL = portletPreferences.getValue("gridit_trodan_RENEWAL", "checked");
        // Getting the TRODAN DISABLEVOMS from the portlet preferences for the GRIDIT VO
        String gridit_trodan_DISABLEVOMS = portletPreferences.getValue("gridit_trodan_DISABLEVOMS", "unchecked");

        // Getting the TRODAN INFRASTRUCTURE from the portlet preferences for the EUMED VO
        String eumed_trodan_INFRASTRUCTURE = portletPreferences.getValue("eumed_trodan_INFRASTRUCTURE", "N/A");
        // Getting the TRODAN VONAME from the portlet preferences for the EUMED VO
        String eumed_trodan_VONAME = portletPreferences.getValue("eumed_trodan_VONAME", "N/A");
        // Getting the TRODAN TOPPBDII from the portlet preferences for the EUMED VO
        String eumed_trodan_TOPBDII = portletPreferences.getValue("eumed_trodan_TOPBDII", "N/A");
        // Getting the TRODAN WMS from the portlet preferences for the EUMED VO
        String[] eumed_trodan_WMS = portletPreferences.getValues("eumed_trodan_WMS", new String[5]);
        // Getting the TRODAN ETOKENSERVER from the portlet preferences for the EUMED VO
        String eumed_trodan_ETOKENSERVER = portletPreferences.getValue("eumed_trodan_ETOKENSERVER", "N/A");
        // Getting the TRODAN MYPROXYSERVER from the portlet preferences for the EUMED VO
        String eumed_trodan_MYPROXYSERVER = portletPreferences.getValue("eumed_trodan_MYPROXYSERVER", "N/A");
        // Getting the TRODAN PORT from the portlet preferences for the EUMED VO
        String eumed_trodan_PORT = portletPreferences.getValue("eumed_trodan_PORT", "N/A");
        // Getting the TRODAN METADATASERVER from the portlet preferences for the EUMED VO
        String eumed_trodan_METADATASERVER = portletPreferences.getValue("eumed_trodan_METADATASERVER", "N/A");
        // Getting the TRODAN METADATASERVER_PORT from the portlet preferences for the EUMED VO
        String eumed_trodan_METADATAPORT = portletPreferences.getValue("eumed_trodan_METADATAPORT", "N/A");
        // Getting the TRODAN ROBOTID from the portlet preferences for the EUMED VO
        String eumed_trodan_ROBOTID = portletPreferences.getValue("eumed_trodan_ROBOTID", "N/A");
        // Getting the TRODAN ROLE from the portlet preferences for the EUMED VO
        String eumed_trodan_ROLE = portletPreferences.getValue("eumed_trodan_ROLE", "N/A");
        // Getting the TRODAN RENEWAL from the portlet preferences for the EUMED VO
        String eumed_trodan_RENEWAL = portletPreferences.getValue("eumed_trodan_RENEWAL", "checked");
        // Getting the TRODAN DISABLEVOMS from the portlet preferences for the EUMED VO
        String eumed_trodan_DISABLEVOMS = portletPreferences.getValue("eumed_trodan_DISABLEVOMS", "unchecked");

        // Getting the TRODAN INFRASTRUCTURE from the portlet preferences for the BIOMED VO
        String biomed_trodan_INFRASTRUCTURE = portletPreferences.getValue("biomed_trodan_INFRASTRUCTURE", "N/A");
        // Getting the TRODAN VONAME from the portlet preferences for the BIOMED VO
        String biomed_trodan_VONAME = portletPreferences.getValue("biomed_trodan_VONAME", "N/A");
        // Getting the TRODAN TOPPBDII from the portlet preferences for the BIOMED VO
        String biomed_trodan_TOPBDII = portletPreferences.getValue("biomed_trodan_TOPBDII", "N/A");
        // Getting the TRODAN WMS from the portlet preferences for the BIOMED VO
        String[] biomed_trodan_WMS = portletPreferences.getValues("biomed_trodan_WMS", new String[5]);
        // Getting the TRODAN ETOKENSERVER from the portlet preferences for the BIOMED VO
        String biomed_trodan_ETOKENSERVER = portletPreferences.getValue("biomed_trodan_ETOKENSERVER", "N/A");
        // Getting the TRODAN MYPROXYSERVER from the portlet preferences for the BIOMED VO
        String biomed_trodan_MYPROXYSERVER = portletPreferences.getValue("biomed_trodan_MYPROXYSERVER", "N/A");
        // Getting the TRODAN PORT from the portlet preferences for the BIOMED VO
        String biomed_trodan_PORT = portletPreferences.getValue("biomed_trodan_PORT", "N/A");
        // Getting the TRODAN METADATASERVER from the portlet preferences for the BIOMED VO
        String biomed_trodan_METADATASERVER = portletPreferences.getValue("biomed_trodan_METADATASERVER", "N/A");
        // Getting the TRODAN METADATASERVER_PORT from the portlet preferences for the BIOMED VO
        String biomed_trodan_METADATAPORT = portletPreferences.getValue("biomed_trodan_METADATAPORT", "N/A");
        // Getting the TRODAN ROBOTID from the portlet preferences for the BIOMED VO
        String biomed_trodan_ROBOTID = portletPreferences.getValue("biomed_trodan_ROBOTID", "N/A");
        // Getting the TRODAN ROLE from the portlet preferences for the BIOMED VO
        String biomed_trodan_ROLE = portletPreferences.getValue("biomed_trodan_ROLE", "N/A");
        // Getting the TRODAN RENEWAL from the portlet preferences for the BIOMED VO
        String biomed_trodan_RENEWAL = portletPreferences.getValue("biomed_trodan_RENEWAL", "checked");
        // Getting the TRODAN DISABLEVOMS from the portlet preferences for the BIOMED VO
        String biomed_trodan_DISABLEVOMS = portletPreferences.getValue("biomed_trodan_DISABLEVOMS", "unchecked");

        // Getting the TRODAN APPID from the portlet preferences
        String trodan_APPID = portletPreferences.getValue("trodan_APPID", "N/A");
        // Get the TRODAN LOG LEVEL from the portlet preferences
        String trodan_LOGLEVEL = portletPreferences.getValue("trodan_LOGLEVEL", "INFO");
        // Getting the TRODAN METADATA REPOSITORY from the portlet preferences
        String trodan_REPOSITORY = portletPreferences.getValue("trodan_REPOSITORY", "N/A");               
        // Getting the TRODAN OUTPUT_PATH from the portlet preferences
        String trodan_OUTPUT_PATH = portletPreferences.getValue("trodan_OUTPUT_PATH", "/tmp");
        // Getting the TRODAN SFOTWARE from the portlet preferences
        String trodan_SOFTWARE = portletPreferences.getValue("trodan_SOFTWARE", "N/A");
        // Getting the TRACKING_DB_HOSTNAME from the portlet preferences
        String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
        // Getting the TRACKING_DB_USERNAME from the portlet preferences
        String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
        // Getting the TRACKING_DB_PASSWORD from the portlet preferences
        String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD", "N/A");
        // Getting the SMTP_HOST from the portlet preferences
        String SMTP_HOST = portletPreferences.getValue("SMTP_HOST", "N/A");
        // Getting the SENDER MAIL from the portlet preferences
        String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL", "N/A");
        // Get the list of enabled Infrastructures
        String[] infras = portletPreferences.getValues("trodan_ENABLEINFRASTRUCTURE", new String[3]);

        // Set the default portlet preferences
        request.setAttribute("gridit_trodan_INFRASTRUCTURE", gridit_trodan_INFRASTRUCTURE.trim());
        request.setAttribute("gridit_trodan_VONAME", gridit_trodan_VONAME.trim());
        request.setAttribute("gridit_trodan_TOPBDII", gridit_trodan_TOPBDII.trim());
        request.setAttribute("gridit_trodan_WMS", gridit_trodan_WMS);
        request.setAttribute("gridit_trodan_ETOKENSERVER", gridit_trodan_ETOKENSERVER.trim());
        request.setAttribute("gridit_trodan_MYPROXYSERVER", gridit_trodan_MYPROXYSERVER.trim());
        request.setAttribute("gridit_trodan_PORT", gridit_trodan_PORT.trim());
        request.setAttribute("gridit_trodan_METADATASERVER", gridit_trodan_METADATASERVER.trim());
        request.setAttribute("gridit_trodan_METADATAPORT", gridit_trodan_METADATAPORT.trim());
        request.setAttribute("gridit_trodan_ROBOTID", gridit_trodan_ROBOTID.trim());
        request.setAttribute("gridit_trodan_ROLE", gridit_trodan_ROLE.trim());
        request.setAttribute("gridit_trodan_RENEWAL", gridit_trodan_RENEWAL);
        request.setAttribute("gridit_trodan_DISABLEVOMS", gridit_trodan_DISABLEVOMS);
        
        request.setAttribute("cometa_trodan_INFRASTRUCTURE", cometa_trodan_INFRASTRUCTURE.trim());
        request.setAttribute("cometa_trodan_VONAME", cometa_trodan_VONAME.trim());
        request.setAttribute("cometa_trodan_TOPBDII", cometa_trodan_TOPBDII.trim());
        request.setAttribute("cometa_trodan_WMS", cometa_trodan_WMS);
        request.setAttribute("cometa_trodan_ETOKENSERVER", cometa_trodan_ETOKENSERVER.trim());
        request.setAttribute("cometa_trodan_MYPROXYSERVER", cometa_trodan_MYPROXYSERVER.trim());
        request.setAttribute("cometa_trodan_PORT", cometa_trodan_PORT.trim());
        request.setAttribute("cometa_trodan_METADATASERVER", cometa_trodan_METADATASERVER.trim());
        request.setAttribute("cometa_trodan_METADATAPORT", cometa_trodan_METADATAPORT.trim());
        request.setAttribute("cometa_trodan_ROBOTID", cometa_trodan_ROBOTID.trim());
        request.setAttribute("cometa_trodan_ROLE", cometa_trodan_ROLE.trim());
        request.setAttribute("cometa_trodan_RENEWAL", cometa_trodan_RENEWAL);
        request.setAttribute("cometa_trodan_DISABLEVOMS", cometa_trodan_DISABLEVOMS);

        request.setAttribute("eumed_trodan_INFRASTRUCTURE", eumed_trodan_INFRASTRUCTURE.trim());
        request.setAttribute("eumed_trodan_VONAME", eumed_trodan_VONAME.trim());
        request.setAttribute("eumed_trodan_TOPBDII", eumed_trodan_TOPBDII.trim());
        request.setAttribute("eumed_trodan_WMS", eumed_trodan_WMS);
        request.setAttribute("eumed_trodan_ETOKENSERVER", eumed_trodan_ETOKENSERVER.trim());
        request.setAttribute("eumed_trodan_MYPROXYSERVER", eumed_trodan_MYPROXYSERVER.trim());
        request.setAttribute("eumed_trodan_PORT", eumed_trodan_PORT.trim());
        request.setAttribute("eumed_trodan_METADATASERVER", eumed_trodan_METADATASERVER.trim());
        request.setAttribute("eumed_trodan_METADATAPORT", eumed_trodan_METADATAPORT.trim());
        request.setAttribute("eumed_trodan_ROBOTID", eumed_trodan_ROBOTID.trim());
        request.setAttribute("eumed_trodan_ROLE", eumed_trodan_ROLE.trim());
        request.setAttribute("eumed_trodan_RENEWAL", eumed_trodan_RENEWAL);
        request.setAttribute("eumed_trodan_DISABLEVOMS", eumed_trodan_DISABLEVOMS);

        request.setAttribute("biomed_trodan_INFRASTRUCTURE", biomed_trodan_INFRASTRUCTURE.trim());
        request.setAttribute("biomed_trodan_VONAME", biomed_trodan_VONAME.trim());
        request.setAttribute("biomed_trodan_TOPBDII", biomed_trodan_TOPBDII.trim());
        request.setAttribute("biomed_trodan_WMS", biomed_trodan_WMS);
        request.setAttribute("biomed_trodan_ETOKENSERVER", biomed_trodan_ETOKENSERVER.trim());
        request.setAttribute("biomed_trodan_MYPROXYSERVER", biomed_trodan_MYPROXYSERVER.trim());
        request.setAttribute("biomed_trodan_PORT", biomed_trodan_PORT.trim());
        request.setAttribute("biomed_trodan_METADATASERVER", biomed_trodan_METADATASERVER.trim());
        request.setAttribute("biomed_trodan_METADATAPORT", biomed_trodan_METADATAPORT.trim());
        request.setAttribute("biomed_trodan_ROBOTID", biomed_trodan_ROBOTID.trim());
        request.setAttribute("biomed_trodan_ROLE", biomed_trodan_ROLE.trim());
        request.setAttribute("biomed_trodan_RENEWAL", biomed_trodan_RENEWAL);
        request.setAttribute("biomed_trodan_DISABLEVOMS", biomed_trodan_DISABLEVOMS);

        request.setAttribute("trodan_ENABLEINFRASTRUCTURE", infras);
        request.setAttribute("trodan_APPID", trodan_APPID.trim());
        request.setAttribute("trodan_LOGLEVEL", trodan_LOGLEVEL.trim());
        request.setAttribute("trodan_OUTPUT_PATH", trodan_OUTPUT_PATH.trim());
        request.setAttribute("trodan_SOFTWARE", trodan_SOFTWARE.trim());
        request.setAttribute("trodan_REPOSITORY", trodan_REPOSITORY.trim());
        request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
        request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
        request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
        request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
        request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());

        log.info("\nStarting the EDIT mode...with this settings"
        + "\ncometa_trodan_INFRASTRUCTURE: " + cometa_trodan_INFRASTRUCTURE
        + "\ncometa_trodan_VONAME: " + cometa_trodan_VONAME
        + "\ncometa_trodan_TOPBDII: " + cometa_trodan_TOPBDII                    
        + "\ncometa_trodan_ETOKENSERVER: " + cometa_trodan_ETOKENSERVER
        + "\ncometa_trodan_MYPROXYSERVER: " + cometa_trodan_MYPROXYSERVER
        + "\ncometa_trodan_PORT: " + cometa_trodan_PORT
        + "\ncometa_trodan_METADATASERVER: " + cometa_trodan_METADATASERVER
        + "\ncometa_trodan_METADATAPORT: " + cometa_trodan_METADATAPORT
        + "\ncometa_trodan_ROBOTID: " + cometa_trodan_ROBOTID
        + "\ncometa_trodan_ROLE: " + cometa_trodan_ROLE
        + "\ncometa_trodan_RENEWAL: " + cometa_trodan_RENEWAL
        + "\ncometa_trodan_DISABLEVOMS: " + cometa_trodan_DISABLEVOMS
                
        + "\ngridit_trodan_INFRASTRUCTURE: " + gridit_trodan_INFRASTRUCTURE
        + "\ngridit_trodan_VONAME: " + gridit_trodan_VONAME
        + "\ngridit_trodan_TOPBDII: " + gridit_trodan_TOPBDII                    
        + "\ngridit_trodan_ETOKENSERVER: " + gridit_trodan_ETOKENSERVER
        + "\ngridit_trodan_MYPROXYSERVER: " + gridit_trodan_MYPROXYSERVER
        + "\ngridit_trodan_PORT: " + gridit_trodan_PORT
        + "\ngridit_trodan_METADATASERVER: " + gridit_trodan_METADATASERVER
        + "\ngridit_trodan_METADATAPORT: " + gridit_trodan_METADATAPORT
        + "\ngridit_trodan_ROBOTID: " + gridit_trodan_ROBOTID
        + "\ngridit_trodan_ROLE: " + gridit_trodan_ROLE
        + "\ngridit_trodan_RENEWAL: " + gridit_trodan_RENEWAL
        + "\ngridit_trodan_DISABLEVOMS: " + gridit_trodan_DISABLEVOMS

        + "\n\neumed_trodan_INFRASTRUCTURE: " + eumed_trodan_INFRASTRUCTURE
        + "\neumed_trodan_VONAME: " + eumed_trodan_VONAME
        + "\neumed_trodan_TOPBDII: " + eumed_trodan_TOPBDII                    
        + "\neumed_trodan_ETOKENSERVER: " + eumed_trodan_ETOKENSERVER
        + "\neumed_trodan_MYPROXYSERVER: " + eumed_trodan_MYPROXYSERVER
        + "\neumed_trodan_PORT: " + eumed_trodan_PORT
        + "\neumed_trodan_METADATASERVER: " + eumed_trodan_METADATASERVER
        + "\neumed_trodan_METADATAPORT: " + eumed_trodan_METADATAPORT
        + "\neumed_trodan_ROBOTID: " + eumed_trodan_ROBOTID
        + "\neumed_trodan_ROLE: " + eumed_trodan_ROLE
        + "\neumed_trodan_RENEWAL: " + eumed_trodan_RENEWAL
        + "\neumed_trodan_DISABLEVOMS: " + eumed_trodan_DISABLEVOMS

        + "\n\nbiomed_trodan_INFRASTRUCTURE: " + biomed_trodan_INFRASTRUCTURE
        + "\nbiomed_trodan_VONAME: " + biomed_trodan_VONAME
        + "\nbiomed_trodan_TOPBDII: " + biomed_trodan_TOPBDII                   
        + "\nbiomed_trodan_ETOKENSERVER: " + biomed_trodan_ETOKENSERVER
        + "\nbiomed_trodan_MYPROXYSERVER: " + biomed_trodan_MYPROXYSERVER
        + "\nbiomed_trodan_PORT: " + biomed_trodan_PORT
        + "\nbiomed_trodan_METADATASERVER: " + biomed_trodan_METADATASERVER
        + "\nbiomed_trodan_METADATAPORT: " + biomed_trodan_METADATAPORT
        + "\nbiomed_trodan_ROBOTID: " + biomed_trodan_ROBOTID
        + "\nbiomed_trodan_ROLE: " + biomed_trodan_ROLE
        + "\nbiomed_trodan_RENEWAL: " + biomed_trodan_RENEWAL
        + "\nbiomed_trodan_DISABLEVOMS: " + biomed_trodan_DISABLEVOMS
        
        + "\ntrodan_APPID: " + trodan_APPID
        + "\ntrodan_LOGLEVEL: " + trodan_LOGLEVEL
        + "\ntrodan_OUTPUT_PATH: " + trodan_OUTPUT_PATH
        + "\ntrodan_SOFTWARE: " +trodan_SOFTWARE
        + "\ntrodan_REPOSITORY: " +trodan_REPOSITORY
        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
        + "\nSMTP Server: " + SMTP_HOST
        + "\nSender: " + SENDER_MAIL);

        PortletRequestDispatcher dispatcher =
                getPortletContext().getRequestDispatcher("/edit.jsp");

        dispatcher.include(request, response);
    }

    @Override
    protected void doHelp(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        //super.doHelp(request, response);

        response.setContentType("text/html");

        log.info("\nStarting the HELP mode...");
        PortletRequestDispatcher dispatcher =
                getPortletContext().getRequestDispatcher("/help.jsp");

        dispatcher.include(request, response);
    }

    @Override
    protected void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        response.setContentType("text/html");

        //java.util.Enumeration listPreferences = portletPreferences.getNames();
        PortletRequestDispatcher dispatcher = null;
        
        String cometa_trodan_TOPBDII = "";
        String cometa_trodan_VONAME = "";
        String gridit_trodan_TOPBDII = "";
        String gridit_trodan_VONAME = "";
        String eumed_trodan_TOPBDII = "";
        String eumed_trodan_VONAME = "";
        String biomed_trodan_TOPBDII = "";
        String biomed_trodan_VONAME = "";
        
        String cometa_trodan_ENABLEINFRASTRUCTURE = "";
        String gridit_trodan_ENABLEINFRASTRUCTURE = "";
        String eumed_trodan_ENABLEINFRASTRUCTURE = "";
        String biomed_trodan_ENABLEINFRASTRUCTURE = "";
        String[] infras = new String[4];
        
        String[] trodan_INFRASTRUCTURES = 
                portletPreferences.getValues("trodan_ENABLEINFRASTRUCTURE", new String[4]);
        
        for (int i=0; i<trodan_INFRASTRUCTURES.length; i++) {            
            if (trodan_INFRASTRUCTURES[i]!=null && trodan_INFRASTRUCTURES[i].equals("cometa")) 
                { cometa_trodan_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n COMETA!"); }
            if (trodan_INFRASTRUCTURES[i]!=null && trodan_INFRASTRUCTURES[i].equals("gridit")) 
                { gridit_trodan_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GRIDIT!"); }
            if (trodan_INFRASTRUCTURES[i]!=null && trodan_INFRASTRUCTURES[i].equals("eumed")) 
                { eumed_trodan_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n EUMED!"); }
            if (trodan_INFRASTRUCTURES[i]!=null && trodan_INFRASTRUCTURES[i].equals("biomed")) 
                { biomed_trodan_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n BIOMED!"); }            
        }
        
        // Getting the TRODAN APPID from the portlet preferences
        String trodan_APPID = portletPreferences.getValue("trodan_APPID", "N/A");
        // Get the LOGLEVEL from the portlet preferences
        String trodan_LOGLEVEL = portletPreferences.getValue("trodan_LOGLEVEL", "INFO");
        // Getting the TRODAN OUTPUT_PATH from the portlet preferences
        String trodan_OUTPUT_PATH = portletPreferences.getValue("trodan_OUTPUT_PATH", "/tmp");
        // Getting the TRODAN SOFTWARE from the portlet preferences
        String trodan_SOFTWARE = portletPreferences.getValue("trodan_SOFTWARE", "N/A");
        // Getting the TRODAN METADATARESPOSITORY from the portlet preferences
        String trodan_REPOSITORY = portletPreferences.getValue("trodan_REPOSITORY", "N/A");
        // Getting the TRACKING_DB_HOSTNAME from the portlet preferences
        String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
        // Getting the TRACKING_DB_USERNAME from the portlet preferences
        String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
        // Getting the TRACKING_DB_PASSWORD from the portlet preferences
        String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD", "N/A");
        // Getting the SMTP_HOST from the portlet preferences
        String SMTP_HOST = portletPreferences.getValue("SMTP_HOST", "N/A");
        // Getting the SENDER_MAIL from the portlet preferences
        String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL", "N/A");
        
        // List of TRODAN stations for the EUMED VO
        List<String> stations = null;
        
        if (cometa_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[0]="cometa";
            // Getting the TRODAN INFRASTRUCTURE from the portlet preferences for the COMETA VO
            String cometa_trodan_INFRASTRUCTURE = portletPreferences.getValue("cometa_trodan_INFRASTRUCTURE", "N/A");
            // Getting the TRODAN VONAME from the portlet preferences for the COMETA VO
            cometa_trodan_VONAME = portletPreferences.getValue("cometa_trodan_VONAME", "N/A");
            // Getting the TRODAN TOPPBDII from the portlet preferences for the COMETA VO
            cometa_trodan_TOPBDII = portletPreferences.getValue("cometa_trodan_TOPBDII", "N/A");
            // Getting the TRODAN WMS from the portlet preferences for the COMETA VO
            String[] cometa_trodan_WMS = portletPreferences.getValues("cometa_trodan_WMS", new String[5]);
            // Getting the TRODAN ETOKENSERVER from the portlet preferences for the COMETA VO
            String cometa_trodan_ETOKENSERVER = portletPreferences.getValue("cometa_trodan_ETOKENSERVER", "N/A");
            // Getting the TRODAN MYPROXYSERVER from the portlet preferences for the COMETA VO
            String cometa_trodan_MYPROXYSERVER = portletPreferences.getValue("cometa_trodan_MYPROXYSERVER", "N/A");
            // Getting the TRODAN PORT from the portlet preferences for the COMETA VO
            String cometa_trodan_PORT = portletPreferences.getValue("cometa_trodan_PORT", "N/A");
            // Getting the TRODAN METADATASERVER from the portlet preferences for the COMETA VO
            String cometa_trodan_METADATASERVER = portletPreferences.getValue("cometa_trodan_METADATASERVER", "N/A");
            // Getting the TRODAN METADATAPORT from the portlet preferences for the COMETA VO
            String cometa_trodan_METADATAPORT = portletPreferences.getValue("cometa_trodan_METADATAPORT", "N/A");            
            // Getting the TRODAN ROBOTID from the portlet preferences for the COMETA VO
            String cometa_trodan_ROBOTID = portletPreferences.getValue("gridit_trodan_ROBOTID", "N/A");
            // Getting the TRODAN ROLE from the portlet preferences for the COMETA VO
            String cometa_trodan_ROLE = portletPreferences.getValue("cometa_trodan_ROLE", "N/A");
            // Getting the TRODAN RENEWAL from the portlet preferences for the COMETA VO
            String cometa_trodan_RENEWAL = portletPreferences.getValue("cometa_trodan_RENEWAL", "checked");
            // Getting the TRODAN DISABLEVOMS from the portlet preferences for the COMETA VO
            String cometa_trodan_DISABLEVOMS = portletPreferences.getValue("cometa_trodan_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the COMETA VO
            String cometa_WMS = "";
            if (cometa_trodan_ENABLEINFRASTRUCTURE.equals("checked")) {
                if (cometa_trodan_WMS!=null) {
                    //log.info("length="+cometa_trodan_WMS.length);
                    for (int i = 0; i < cometa_trodan_WMS.length; i++)
                        if (!(cometa_trodan_WMS[i].trim().equals("N/A")) ) 
                            cometa_WMS += cometa_trodan_WMS[i] + " ";                        
                } else { log.info("WMS not set for COMETA!"); cometa_trodan_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("cometa_trodan_INFRASTRUCTURE", cometa_trodan_INFRASTRUCTURE.trim());
            request.setAttribute("cometa_trodan_VONAME", cometa_trodan_VONAME.trim());
            request.setAttribute("cometa_trodan_TOPBDII", cometa_trodan_TOPBDII.trim());
            request.setAttribute("cometa_trodan_WMS", cometa_WMS);
            request.setAttribute("cometa_trodan_ETOKENSERVER", cometa_trodan_ETOKENSERVER.trim());
            request.setAttribute("cometa_trodan_MYPROXYSERVER", cometa_trodan_MYPROXYSERVER.trim());
            request.setAttribute("cometa_trodan_PORT", cometa_trodan_PORT.trim());
            request.setAttribute("cometa_trodan_METADATASERVER", cometa_trodan_METADATASERVER.trim());
            request.setAttribute("cometa_trodan_METADATAPORT", cometa_trodan_METADATAPORT.trim());
            request.setAttribute("cometa_trodan_ROBOTID", cometa_trodan_ROBOTID.trim());
            request.setAttribute("cometa_trodan_ROLE", cometa_trodan_ROLE.trim());
            request.setAttribute("cometa_trodan_RENEWAL", cometa_trodan_RENEWAL);
            request.setAttribute("cometa_trodan_DISABLEVOMS", cometa_trodan_DISABLEVOMS);
            
            //request.setAttribute("trodan_ENABLEINFRASTRUCTURE", trodan_ENABLEINFRASTRUCTURE);
            request.setAttribute("trodan_APPID", trodan_APPID.trim());
            request.setAttribute("trodan_LOGLEVEL", trodan_LOGLEVEL.trim());
            request.setAttribute("trodan_SOFTWARE", trodan_SOFTWARE.trim());
            request.setAttribute("trodan_REPOSITORY", trodan_REPOSITORY.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (gridit_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[1]="gridit";
            // Getting the TRODAN INFRASTRUCTURE from the portlet preferences for the GRIDIT VO
            String gridit_trodan_INFRASTRUCTURE = portletPreferences.getValue("gridit_trodan_INFRASTRUCTURE", "N/A");
            // Getting the TRODAN VONAME from the portlet preferences for the GRIDIT VO
            gridit_trodan_VONAME = portletPreferences.getValue("gridit_trodan_VONAME", "N/A");
            // Getting the TRODAN TOPPBDII from the portlet preferences for the GRIDIT VO
            gridit_trodan_TOPBDII = portletPreferences.getValue("gridit_trodan_TOPBDII", "N/A");
            // Getting the TRODAN WMS from the portlet preferences for the GRIDIT VO
            String[] gridit_trodan_WMS = portletPreferences.getValues("gridit_trodan_WMS", new String[5]);
            // Getting the TRODAN ETOKENSERVER from the portlet preferences for the GRIDIT VO
            String gridit_trodan_ETOKENSERVER = portletPreferences.getValue("gridit_trodan_ETOKENSERVER", "N/A");
            // Getting the TRODAN MYPROXYSERVER from the portlet preferences for the GRIDIT VO
            String gridit_trodan_MYPROXYSERVER = portletPreferences.getValue("gridit_trodan_MYPROXYSERVER", "N/A");
            // Getting the TRODAN PORT from the portlet preferences for the GRIDIT VO
            String gridit_trodan_PORT = portletPreferences.getValue("gridit_trodan_PORT", "N/A");
            // Getting the TRODAN METADATASERVER from the portlet preferences for the GRIDIT VO
            String gridit_trodan_METADATASERVER = portletPreferences.getValue("gridit_trodan_METADATASERVER", "N/A");
            // Getting the TRODAN METADATAPORT from the portlet preferences for the GRIDIT VO
            String gridit_trodan_METADATAPORT = portletPreferences.getValue("gridit_trodan_METADATAPORT", "N/A");
            // Getting the TRODAN ROBOTID from the portlet preferences for the GRIDIT VO
            String gridit_trodan_ROBOTID = portletPreferences.getValue("gridit_trodan_ROBOTID", "N/A");
            // Getting the TRODAN ROLE from the portlet preferences for the GRIDIT VO
            String gridit_trodan_ROLE = portletPreferences.getValue("gridit_trodan_ROLE", "N/A");
            // Getting the TRODAN RENEWAL from the portlet preferences for the GRIDIT VO
            String gridit_trodan_RENEWAL = portletPreferences.getValue("gridit_trodan_RENEWAL", "checked");
            // Getting the TRODAN DISABLEVOMS from the portlet preferences for the GRIDIT VO
            String gridit_trodan_DISABLEVOMS = portletPreferences.getValue("gridit_trodan_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the GRIDIT VO
            String gridit_WMS = "";
            if (gridit_trodan_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (gridit_trodan_WMS!=null) {
                    //log.info("length="+gridit_trodan_WMS.length);
                    for (int i = 0; i < gridit_trodan_WMS.length; i++)
                        if (!(gridit_trodan_WMS[i].trim().equals("N/A")) ) 
                            gridit_WMS += gridit_trodan_WMS[i] + " ";                        
                } else { log.info("WMS not set for GRIDIT!"); gridit_trodan_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("gridit_trodan_INFRASTRUCTURE", gridit_trodan_INFRASTRUCTURE.trim());
            request.setAttribute("gridit_trodan_VONAME", gridit_trodan_VONAME.trim());
            request.setAttribute("gridit_trodan_TOPBDII", gridit_trodan_TOPBDII.trim());
            request.setAttribute("gridit_trodan_WMS", gridit_WMS);
            request.setAttribute("gridit_trodan_ETOKENSERVER", gridit_trodan_ETOKENSERVER.trim());
            request.setAttribute("gridit_trodan_MYPROXYSERVER", gridit_trodan_MYPROXYSERVER.trim());
            request.setAttribute("gridit_trodan_PORT", gridit_trodan_PORT.trim());
            request.setAttribute("gridit_trodan_METADATASERVER", gridit_trodan_METADATASERVER.trim());
            request.setAttribute("gridit_trodan_METADATAPORT", gridit_trodan_METADATAPORT.trim());
            request.setAttribute("gridit_trodan_ROBOTID", gridit_trodan_ROBOTID.trim());
            request.setAttribute("gridit_trodan_ROLE", gridit_trodan_ROLE.trim());
            request.setAttribute("gridit_trodan_RENEWAL", gridit_trodan_RENEWAL);
            request.setAttribute("gridit_trodan_DISABLEVOMS", gridit_trodan_DISABLEVOMS);
            
            //request.setAttribute("trodan_ENABLEINFRASTRUCTURE", trodan_ENABLEINFRASTRUCTURE);
            request.setAttribute("trodan_APPID", trodan_APPID.trim());
            request.setAttribute("trodan_LOGLEVEL", trodan_LOGLEVEL.trim());
            request.setAttribute("trodan_OUTPUT_PATH", trodan_OUTPUT_PATH.trim());
            request.setAttribute("trodan_SOFTWARE", trodan_SOFTWARE.trim());
            request.setAttribute("trodan_REPOSITORY", trodan_REPOSITORY.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (eumed_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[2]="eumed";
            
            // Getting the TRODAN INFRASTRUCTURE from the portlet preferences for the EUMED VO
            String eumed_trodan_INFRASTRUCTURE = portletPreferences.getValue("eumed_trodan_INFRASTRUCTURE", "N/A");
            // Getting the TRODAN VONAME from the portlet preferences for the EUMED VO
            eumed_trodan_VONAME = portletPreferences.getValue("eumed_trodan_VONAME", "N/A");
            // Getting the TRODAN TOPPBDII from the portlet preferences for the EUMED VO
            eumed_trodan_TOPBDII = portletPreferences.getValue("eumed_trodan_TOPBDII", "N/A");
            // Getting the TRODAN WMS from the portlet preferences for the EUMED VO
            String[] eumed_trodan_WMS = portletPreferences.getValues("eumed_trodan_WMS", new String[5]);
            // Getting the TRODAN ETOKENSERVER from the portlet preferences for the EUMED VO
            String eumed_trodan_ETOKENSERVER = portletPreferences.getValue("eumed_trodan_ETOKENSERVER", "N/A");
            // Getting the TRODAN MYPROXYSERVER from the portlet preferences for the EUMED VO
            String eumed_trodan_MYPROXYSERVER = portletPreferences.getValue("eumed_trodan_MYPROXYSERVER", "N/A");
            // Getting the TRODAN PORT from the portlet preferences for the EUMED VO
            String eumed_trodan_PORT = portletPreferences.getValue("eumed_trodan_PORT", "N/A");
            // Getting the TRODAN METADATASERVER from the portlet preferences for the EUMED VO
            String eumed_trodan_METADATASERVER = portletPreferences.getValue("eumed_trodan_METADATASERVER", "N/A");
            // Getting the TRODAN METADATAPORT from the portlet preferences for the EUMED VO
            String eumed_trodan_METADATAPORT = portletPreferences.getValue("eumed_trodan_METADATAPORT", "N/A");
            // Getting the TRODAN ROBOTID from the portlet preferences for the EUMED VO
            String eumed_trodan_ROBOTID = portletPreferences.getValue("eumed_trodan_ROBOTID", "N/A");
            // Getting the TRODAN ROLE from the portlet preferences for the EUMED VO
            String eumed_trodan_ROLE = portletPreferences.getValue("eumed_trodan_ROLE", "N/A");
            // Getting the TRODAN RENEWAL from the portlet preferences for the EUMED VO
            String eumed_trodan_RENEWAL = portletPreferences.getValue("eumed_trodan_RENEWAL", "checked");
            // Getting the TRODAN DISABLEVOMS from the portlet preferences for the EUMED VO
            String eumed_trodan_DISABLEVOMS = portletPreferences.getValue("eumed_trodan_DISABLEVOMS", "unchecked");
                                    
            // Fetching all the WMS Endpoints for the EUMED VO
            String eumed_WMS = "";
            if (eumed_trodan_ENABLEINFRASTRUCTURE.equals("checked")) {
                if (eumed_trodan_WMS!=null) {
                    //log.info("length="+eumed_trodan_WMS.length);
                    for (int i = 0; i < eumed_trodan_WMS.length; i++)
                        if (!(eumed_trodan_WMS[i].trim().equals("N/A")) ) 
                            eumed_WMS += eumed_trodan_WMS[i] + " ";                        
                } else { log.info("WMS not set for EUMED!"); eumed_trodan_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Fetching the Metadata for the EUMED VO
            if (eumed_trodan_ENABLEINFRASTRUCTURE.equals("checked"))                
            if ( 
                ((!eumed_trodan_METADATASERVER.trim().equals("N/A")) && (!eumed_trodan_METADATASERVER.isEmpty())) 
                &&
                ((!eumed_trodan_METADATASERVER.trim().equals(" ")) && (!eumed_trodan_METADATASERVER.isEmpty())) 
                &&
                ((!eumed_trodan_METADATAPORT.trim().equals("N/A")) && (!eumed_trodan_METADATAPORT.isEmpty())) 
                &&
                ((!eumed_trodan_METADATAPORT.trim().equals(" ")) && (!eumed_trodan_METADATAPORT.isEmpty())) 
               )
            {
                log.info("Fetching the MetaData ... please wait!");
                                
                Properties properties = new Properties();                
                
                properties.setProperty("ACTION", "list");
                properties.setProperty("SERVER_HOST", eumed_trodan_METADATASERVER.trim());
                properties.setProperty("SERVER_PORT", eumed_trodan_METADATAPORT.trim());
                properties.setProperty("REPOSITORY", trodan_REPOSITORY);
                properties.setProperty("TYPE", "DataText");
                
                try {
                    stations = doHTTPRequest(properties, trodan_LOGLEVEL.trim());
                    log.info("Listing the available stations from the repository [ "
                    + trodan_REPOSITORY.toUpperCase()
                    + " ]");
                    
                    for (int i=0; i<stations.size(); i++)
                        log.info(stations.get(i));
                    
                    // collections sort is method to sort List
                    // This will sort List in ascending order
                    Collections.sort(stations);
                    
                } catch (Exception ex) {
                    Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
                }                

            } else log.info("MetaData Server NOT connfigured ... skipping");
            
            // Save the portlet preferences
            request.setAttribute("eumed_trodan_INFRASTRUCTURE", eumed_trodan_INFRASTRUCTURE.trim());
            request.setAttribute("eumed_trodan_VONAME", eumed_trodan_VONAME.trim());
            request.setAttribute("eumed_trodan_TOPBDII", eumed_trodan_TOPBDII.trim());
            request.setAttribute("eumed_trodan_WMS", eumed_WMS);
            request.setAttribute("eumed_trodan_ETOKENSERVER", eumed_trodan_ETOKENSERVER.trim());
            request.setAttribute("eumed_trodan_MYPROXYSERVER", eumed_trodan_MYPROXYSERVER.trim());
            request.setAttribute("eumed_trodan_PORT", eumed_trodan_PORT.trim());
            request.setAttribute("eumed_trodan_METADATASERVER", eumed_trodan_METADATASERVER.trim());
            request.setAttribute("eumed_trodan_METADATAPORT", eumed_trodan_METADATAPORT.trim());
            request.setAttribute("eumed_trodan_ROBOTID", eumed_trodan_ROBOTID.trim());
            request.setAttribute("eumed_trodan_ROLE", eumed_trodan_ROLE.trim());
            request.setAttribute("eumed_trodan_RENEWAL", eumed_trodan_RENEWAL);
            request.setAttribute("eumed_trodan_DISABLEVOMS", eumed_trodan_DISABLEVOMS);

            //request.setAttribute("trodan_ENABLEINFRASTRUCTURE", trodan_ENABLEINFRASTRUCTURE);
            request.setAttribute("trodan_APPID", trodan_APPID.trim());
            request.setAttribute("trodan_LOGLEVEL", trodan_LOGLEVEL.trim());
            request.setAttribute("trodan_OUTPUT_PATH", trodan_OUTPUT_PATH.trim());
            request.setAttribute("trodan_REPOSITORY", trodan_REPOSITORY.trim());
            request.setAttribute("trodan_SOFTWARE", trodan_SOFTWARE.trim());            
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
            request.setAttribute("trodan_STATIONS", stations);
        }

        if (biomed_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[3]="biomed";
            // Getting the TRODAN INFRASTRUCTURE from the portlet preferences for the BIOMED VO
            String biomed_trodan_INFRASTRUCTURE = portletPreferences.getValue("biomed_trodan_INFRASTRUCTURE", "N/A");
            // Getting the TRODAN VONAME from the portlet preferences for the biomed VO
            biomed_trodan_VONAME = portletPreferences.getValue("biomed_trodan_VONAME", "N/A");
            // Getting the TRODAN TOPPBDII from the portlet preferences for the BIOMED VO
            biomed_trodan_TOPBDII = portletPreferences.getValue("biomed_trodan_TOPBDII", "N/A");
            // Getting the TRODAN WMS from the portlet preferences for the BIOMED VO
            String[] biomed_trodan_WMS = portletPreferences.getValues("biomed_trodan_WMS", new String[5]);
            // Getting the TRODAN ETOKENSERVER from the portlet preferences for the BIOMED VO
            String biomed_trodan_ETOKENSERVER = portletPreferences.getValue("biomed_trodan_ETOKENSERVER", "N/A");
            // Getting the TRODAN MYPROXYSERVER from the portlet preferences for the BIOMED VO
            String biomed_trodan_MYPROXYSERVER = portletPreferences.getValue("biomed_trodan_MYPROXYSERVER", "N/A");
            // Getting the TRODAN PORT from the portlet preferences for the BIOMED VO
            String biomed_trodan_PORT = portletPreferences.getValue("biomed_trodan_PORT", "N/A");
            // Getting the TRODAN METADATASERVER from the portlet preferences for the BIOMED VO
            String biomed_trodan_METADATASERVER = portletPreferences.getValue("biomed_trodan_METADATASERVER", "N/A");
            // Getting the TRODAN METADATAPORT from the portlet preferences for the BIOMED VO
            String biomed_trodan_METADATAPORT = portletPreferences.getValue("biomed_trodan_METADATAPORT", "N/A");
            // Getting the TRODAN ROBOTID from the portlet preferences for the BIOMED VO
            String biomed_trodan_ROBOTID = portletPreferences.getValue("biomed_trodan_ROBOTID", "N/A");
            // Getting the TRODAN ROLE from the portlet preferences for the BIOMED VO
            String biomed_trodan_ROLE = portletPreferences.getValue("biomed_trodan_ROLE", "N/A");
            // Getting the TRODAN RENEWAL from the portlet preferences for the BIOMED VO
            String biomed_trodan_RENEWAL = portletPreferences.getValue("biomed_trodan_RENEWAL", "checked");
            // Getting the TRODAN DISABLEVOMS from the portlet preferences for the BIOMED VO
            String biomed_trodan_DISABLEVOMS = portletPreferences.getValue("biomed_trodan_DISABLEVOMS", "unchecked");              
            
            // Fetching all the WMS Endpoints for the BIOMED VO
            String biomed_WMS = "";
            if (biomed_trodan_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (biomed_trodan_WMS!=null) {
                    //log.info("length="+biomed_trodan_WMS.length);
                    for (int i = 0; i < biomed_trodan_WMS.length; i++)
                        if (!(biomed_trodan_WMS[i].trim().equals("N/A")) ) 
                            biomed_WMS += biomed_trodan_WMS[i] + " ";                        
                } else { log.info("WMS not set for BIOMED!"); biomed_trodan_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("biomed_trodan_INFRASTRUCTURE", biomed_trodan_INFRASTRUCTURE.trim());
            request.setAttribute("biomed_trodan_VONAME", biomed_trodan_VONAME.trim());
            request.setAttribute("biomed_trodan_TOPBDII", biomed_trodan_TOPBDII.trim());
            request.setAttribute("biomed_trodan_WMS", biomed_WMS);
            request.setAttribute("biomed_trodan_ETOKENSERVER", biomed_trodan_ETOKENSERVER.trim());
            request.setAttribute("biomed_trodan_MYPROXYSERVER", biomed_trodan_MYPROXYSERVER.trim());
            request.setAttribute("biomed_trodan_PORT", biomed_trodan_PORT.trim());
            request.setAttribute("biomed_trodan_METADATASERVER", biomed_trodan_METADATASERVER.trim());
            request.setAttribute("biomed_trodan_METADATAPORT", biomed_trodan_METADATAPORT.trim());
            request.setAttribute("biomed_trodan_ROBOTID", biomed_trodan_ROBOTID.trim());
            request.setAttribute("biomed_trodan_ROLE", biomed_trodan_ROLE.trim());
            request.setAttribute("biomed_trodan_RENEWAL", biomed_trodan_RENEWAL);
            request.setAttribute("biomed_trodan_DISABLEVOMS", biomed_trodan_DISABLEVOMS);

            //request.setAttribute("trodan_ENABLEINFRASTRUCTURE", trodan_ENABLEINFRASTRUCTURE);
            request.setAttribute("trodan_APPID", trodan_APPID.trim());
            request.setAttribute("trodan_LOGLEVEL", trodan_LOGLEVEL.trim());
            request.setAttribute("trodan_OUTPUT_PATH", trodan_OUTPUT_PATH.trim());
            request.setAttribute("trodan_SOFTWARE", trodan_SOFTWARE.trim());
            request.setAttribute("trodan_REPOSITORY", trodan_REPOSITORY.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        // Save in the preferences the list of supported infrastructures 
        request.setAttribute("trodan_ENABLEINFRASTRUCTURE", infras);

        HashMap<String,Properties> GPS_table = new HashMap<String, Properties>();
        HashMap<String,Properties> GPS_queue = new HashMap<String, Properties>();

        // ********************************************************
        List<String> CEqueues_cometa = null;        
        List<String> CEqueues_gridit = null;
        List<String> CEqueues_eumed = null;
        List<String> CEqueues_biomed = null;
        
        List<String> CEs_list_cometa = null;        
        List<String> CEs_list_gridit = null;        
        List<String> CEs_list_eumed = null;
        List<String> CEs_list_biomed = null;
        
        List<String> CEs_list_TOT = new ArrayList<String>();
        List<String> CEs_queue_TOT = new ArrayList<String>();
        
        BDII bdii = null;

        try {
                if (cometa_trodan_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!cometa_trodan_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<COMETA>*RESOURCES*-----");                    
                    bdii = new BDII(new URI(cometa_trodan_TOPBDII));
                    CEs_list_cometa = 
                            getListofCEForSoftwareTag(cometa_trodan_VONAME,
                                                      cometa_trodan_TOPBDII,
                                                      trodan_SOFTWARE);
                    
                    CEqueues_cometa = 
                            bdii.queryCEQueues(cometa_trodan_VONAME);
                }
             
                if (gridit_trodan_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!gridit_trodan_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<GRIDIT>*RESOURCES*-----");
                    bdii = new BDII(new URI(gridit_trodan_TOPBDII));
                    CEs_list_gridit = 
                               getListofCEForSoftwareTag(gridit_trodan_VONAME,
                                                         gridit_trodan_TOPBDII,
                                                         trodan_SOFTWARE);
                    
                    CEqueues_gridit = 
                            bdii.queryCEQueues(gridit_trodan_VONAME);
                }
                
                if (eumed_trodan_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!eumed_trodan_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<EUMED>*RESOURCES*-----");
                    bdii = new BDII(new URI(eumed_trodan_TOPBDII));
                    CEs_list_eumed = 
                            getListofCEForSoftwareTag(eumed_trodan_VONAME,
                                                      eumed_trodan_TOPBDII,
                                                      trodan_SOFTWARE);
                    
                    CEqueues_eumed = 
                            bdii.queryCEQueues(eumed_trodan_VONAME);
                }
                
                if (biomed_trodan_ENABLEINFRASTRUCTURE.equals("checked") &&
                   (!biomed_trodan_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<BIOMED>*RESOURCES*-----");
                    bdii = new BDII(new URI(biomed_trodan_TOPBDII));
                    CEs_list_biomed = 
                            getListofCEForSoftwareTag(biomed_trodan_VONAME,
                                                      biomed_trodan_TOPBDII,
                                                      trodan_SOFTWARE);
                    
                    CEqueues_biomed = 
                            bdii.queryCEQueues(biomed_trodan_VONAME);
                }
                
                // Merging the list of CEs and queues
                //List<String> CEs_list_TOT = new ArrayList<String>();
                if (cometa_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_cometa);
                if (gridit_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_gridit);
                if (eumed_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_eumed);
                if (biomed_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_biomed);
                
                //List<String> CEs_queue_TOT = new ArrayList<String>();
                if (cometa_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_cometa);
                if (gridit_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_gridit);
                if (eumed_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_eumed);
                if (biomed_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_biomed);                
                
                //=========================================================
                // IMPORTANT: INSTANCIATE THE UsersTrackingDBInterface
                //            CLASS USING THE EMPTY CONSTRUCTOR WHEN
                //            WHEN THE PORTLET IS DEPLOYED IN PRODUCTION!!!
                //=========================================================
                /*UsersTrackingDBInterface DBInterface =
                        new UsersTrackingDBInterface(
                                TRACKING_DB_HOSTNAME.trim(),
                                TRACKING_DB_USERNAME.trim(),
                                TRACKING_DB_PASSWORD.trim());*/
                
                UsersTrackingDBInterface DBInterface =
                        new UsersTrackingDBInterface();
                    
                if ( (CEs_list_TOT != null) && (!CEs_list_TOT.isEmpty()) )
                {
                    log.info("NOT EMPTY LIST!");
                    // Fetching the list of CEs publishing the SW
                    for (String CE:CEs_list_TOT) 
                    {
                        log.info("Fetching the CE = " +CE);
                        Properties coordinates = new Properties();
                        Properties queue = new Properties();

                        float coords[] = DBInterface.getCECoordinate(CE);                        

                        String GPS_LAT = Float.toString(coords[0]);
                        String GPS_LNG = Float.toString(coords[1]);

                        coordinates.setProperty("LAT", GPS_LAT);
                        coordinates.setProperty("LNG", GPS_LNG);

                        // Fetching the Queues
                        for (String CEqueue:CEs_queue_TOT) {
                                if (CEqueue.contains(CE))
                                    queue.setProperty("QUEUE", CEqueue);
                        }

                        // Saving the GPS location in a Java HashMap
                        GPS_table.put(CE, coordinates);

                        // Saving the queue in a Java HashMap
                        GPS_queue.put(CE, queue);
                    }
                } else { log.info ("EMPTY LIST!");
                         stations = new ArrayList<String>();
                         stations.add("N/A");                         
                         request.setAttribute("trodan_STATIONS", stations); 
                }
             } catch (URISyntaxException ex) {
                 
               Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NamingException e) {}

            // Checking the HashMap
            Set set = GPS_table.entrySet();
            Iterator iter = set.iterator();
            while ( iter.hasNext() )
            {
                Map.Entry entry = (Map.Entry)iter.next();
                log.info(" - GPS location of the CE " +
                           entry.getKey() + " => " + entry.getValue());
            }

            // Checking the HashMap
            set = GPS_queue.entrySet();
            iter = set.iterator();
            while ( iter.hasNext() )
            {
                Map.Entry entry = (Map.Entry)iter.next();
                log.info(" - Queue " +
                           entry.getKey() + " => " + entry.getValue());
            }

            Gson gson = new GsonBuilder().create();
            request.setAttribute ("GPS_table", gson.toJson(GPS_table));
            request.setAttribute ("GPS_queue", gson.toJson(GPS_queue));

            dispatcher = getPortletContext().getRequestDispatcher("/view.jsp");
            dispatcher.include(request, response);
    }

    // The init method will be called when installing for the first time the portlet
    // This is the right time to setup the default values into the preferences
    @Override
    public void init() throws PortletException {
        super.init();
    }

    @Override
    public void processAction(ActionRequest request,
                              ActionResponse response)
                throws PortletException, IOException {

        String action = "";
     
        // Getting the action to be processed from the request
        action = request.getParameter("ActionEvent");

        // Determine the username and the email address
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);        
        User user = themeDisplay.getUser();
        String username = user.getScreenName();
        String emailAddress = user.getDisplayEmailAddress();        

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        if (action.equals("CONFIG_TRODAN_PORTLET")) {
            log.info("\nPROCESS ACTION => " + action);
            
            // Getting the TRODAN APPID from the portlet request
            String trodan_APPID = request.getParameter("trodan_APPID");
            // Get the LOGLEVEL from the portlet request
            String trodan_LOGLEVEL = request.getParameter("trodan_LOGLEVEL");
            // Getting the TRODAN METADATAREPOSITORY from the portlet request
            String trodan_REPOSITORY = request.getParameter("trodan_REPOSITORY");
            // Getting the TRODAN OUTPUT_PATH from the portlet request
            String trodan_OUTPUT_PATH = request.getParameter("trodan_OUTPUT_PATH");
            // Getting the TRODAN SOFTWARE from the portlet request
            String trodan_SOFTWARE = request.getParameter("trodan_SOFTWARE");
            // Getting the TRACKING_DB_HOSTNAME from the portlet request
            String TRACKING_DB_HOSTNAME = request.getParameter("TRACKING_DB_HOSTNAME");
            // Getting the TRACKING_DB_USERNAME from the portlet request
            String TRACKING_DB_USERNAME = request.getParameter("TRACKING_DB_USERNAME");
            // Getting the TRACKING_DB_PASSWORD from the portlet request
            String TRACKING_DB_PASSWORD = request.getParameter("TRACKING_DB_PASSWORD");
            // Getting the SMTP_HOST from the portlet request
            String SMTP_HOST = request.getParameter("SMTP_HOST");
            // Getting the SENDER_MAIL from the portlet request
            String SENDER_MAIL = request.getParameter("SENDER_MAIL");            
            
            String[] infras = new String[4];
            
            String cometa_trodan_ENABLEINFRASTRUCTURE = "unchecked";
            String gridit_trodan_ENABLEINFRASTRUCTURE = "unchecked";
            String eumed_trodan_ENABLEINFRASTRUCTURE = "unchecked";
            String biomed_trodan_ENABLEINFRASTRUCTURE = "unchecked";
            
            String[] trodan_INFRASTRUCTURES = request.getParameterValues("trodan_ENABLEINFRASTRUCTURES");         

            if (trodan_INFRASTRUCTURES != null) {
                Arrays.sort(trodan_INFRASTRUCTURES);
                cometa_trodan_ENABLEINFRASTRUCTURE =
                    Arrays.binarySearch(trodan_INFRASTRUCTURES, "cometa") >= 0 ? "checked" : "unchecked";
                gridit_trodan_ENABLEINFRASTRUCTURE =
                    Arrays.binarySearch(trodan_INFRASTRUCTURES, "gridit") >= 0 ? "checked" : "unchecked";
                eumed_trodan_ENABLEINFRASTRUCTURE =
                    Arrays.binarySearch(trodan_INFRASTRUCTURES, "eumed") >= 0 ? "checked" : "unchecked";
                biomed_trodan_ENABLEINFRASTRUCTURE =
                    Arrays.binarySearch(trodan_INFRASTRUCTURES, "biomed") >= 0 ? "checked" : "unchecked";
            }           
            
            if (cometa_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[0]="cometa";
                 // Getting the TRODAN INFRASTRUCTURE from the portlet request for the COMETA VO
                String cometa_trodan_INFRASTRUCTURE = request.getParameter("cometa_trodan_INFRASTRUCTURE");
                // Getting the TRODAN VONAME from the portlet request for the COMETA VO
                String cometa_trodan_VONAME = request.getParameter("cometa_trodan_VONAME");
                // Getting the TRODAN TOPBDII from the portlet request for the COMETA VO
                String cometa_trodan_TOPBDII = request.getParameter("cometa_trodan_TOPBDII");
                // Getting the TRODAN WMS from the portlet request for the COMETA VO
                String[] cometa_trodan_WMS = request.getParameterValues("cometa_trodan_WMS");
                // Getting the TRODAN ETOKENSERVER from the portlet request for the COMETA VO
                String cometa_trodan_ETOKENSERVER = request.getParameter("cometa_trodan_ETOKENSERVER");
                // Getting the TRODAN MYPROXYSERVER from the portlet request for the COMETA VO
                String cometa_trodan_MYPROXYSERVER = request.getParameter("cometa_trodan_MYPROXYSERVER");
                // Getting the TRODAN PORT from the portlet request for the COMETA VO
                String cometa_trodan_PORT = request.getParameter("cometa_trodan_PORT");
                // Getting the TRODAN METADATASERVER from the portlet request for the COMETA VO
                String cometa_trodan_METADATASERVER = request.getParameter("cometa_trodan_METADATASERVER");
                // Getting the TRODAN METADATAPORT from the portlet request for the COMETA VO
                String cometa_trodan_METADATAPORT = request.getParameter("cometa_trodan_METADATAPORT");
                // Getting the TRODAN ROBOTID from the portlet request for the COMETA VO
                String cometa_trodan_ROBOTID = request.getParameter("cometa_trodan_ROBOTID");
                // Getting the TRODAN ROLE from the portlet request for the COMETA VO
                String cometa_trodan_ROLE = request.getParameter("cometa_trodan_ROLE");
                // Getting the TRODAN OPTIONS from the portlet request for the COMETA VO
                String[] cometa_trodan_OPTIONS = request.getParameterValues("cometa_trodan_OPTIONS");

                String cometa_trodan_RENEWAL = "";
                String cometa_trodan_DISABLEVOMS = "";

                if (cometa_trodan_OPTIONS == null){
                    cometa_trodan_RENEWAL = "checked";
                    cometa_trodan_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(cometa_trodan_OPTIONS);
                    // Getting the TRODAN RENEWAL from the portlet preferences for the COMETA VO
                    cometa_trodan_RENEWAL = Arrays.binarySearch(cometa_trodan_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Getting the TRODAN DISABLEVOMS from the portlet preferences for the COMETA VO
                    cometa_trodan_DISABLEVOMS = Arrays.binarySearch(cometa_trodan_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < cometa_trodan_WMS.length; i++)
                    if ( cometa_trodan_WMS[i]!=null && (!cometa_trodan_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] cometa_trodan_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    cometa_trodan_WMS_trimmed[i]=cometa_trodan_WMS[i].trim();
                    log.info ("\n\nCOMETA [" + i + "] WMS=[" + cometa_trodan_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("cometa_trodan_INFRASTRUCTURE", cometa_trodan_INFRASTRUCTURE.trim());
                portletPreferences.setValue("cometa_trodan_VONAME", cometa_trodan_VONAME.trim());
                portletPreferences.setValue("cometa_trodan_TOPBDII", cometa_trodan_TOPBDII.trim());
                portletPreferences.setValues("cometa_trodan_WMS", cometa_trodan_WMS_trimmed);
                portletPreferences.setValue("cometa_trodan_ETOKENSERVER", cometa_trodan_ETOKENSERVER.trim());
                portletPreferences.setValue("cometa_trodan_MYPROXYSERVER", cometa_trodan_MYPROXYSERVER.trim());
                portletPreferences.setValue("cometa_trodan_PORT", cometa_trodan_PORT.trim());
                portletPreferences.setValue("cometa_trodan_METADATASERVER", cometa_trodan_METADATASERVER.trim());
                portletPreferences.setValue("cometa_trodan_METADATAPORT", cometa_trodan_METADATAPORT.trim());
                portletPreferences.setValue("cometa_trodan_ROBOTID", cometa_trodan_ROBOTID.trim());
                portletPreferences.setValue("cometa_trodan_ROLE", cometa_trodan_ROLE.trim());
                portletPreferences.setValue("cometa_trodan_RENEWAL", cometa_trodan_RENEWAL);
                portletPreferences.setValue("cometa_trodan_DISABLEVOMS", cometa_trodan_DISABLEVOMS);                
                
                portletPreferences.setValue("trodan_APPID", trodan_APPID.trim());
                portletPreferences.setValue("trodan_LOGLEVEL", trodan_LOGLEVEL.trim());
                portletPreferences.setValue("trodan_OUTPUT_PATH", trodan_OUTPUT_PATH.trim());
                portletPreferences.setValue("trodan_REPOSITORY", trodan_REPOSITORY.trim());
                portletPreferences.setValue("trodan_SOFTWARE", trodan_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                    + "\n- Storing the TRODAN portlet preferences ..."
                    + "\ncometa_trodan_INFRASTRUCTURE: " + cometa_trodan_INFRASTRUCTURE
                    + "\ncometa_trodan_VONAME: " + cometa_trodan_VONAME
                    + "\ncometa_trodan_TOPBDII: " + cometa_trodan_TOPBDII                    
                    + "\ncometa_trodan_ETOKENSERVER: " + cometa_trodan_ETOKENSERVER
                    + "\ncometa_trodan_MYPROXYSERVER: " + cometa_trodan_MYPROXYSERVER
                    + "\ncometa_trodan_PORT: " + cometa_trodan_PORT
                    + "\ncometa_trodan_METADATASERVER: " + cometa_trodan_METADATASERVER
                    + "\ncometa_trodan_METADATAPORT: " + cometa_trodan_METADATAPORT
                    + "\ncometa_trodan_ROBOTID: " + cometa_trodan_ROBOTID
                    + "\ncometa_trodan_ROLE: " + cometa_trodan_ROLE
                    + "\ncometa_trodan_RENEWAL: " + cometa_trodan_RENEWAL
                    + "\ncometa_trodan_DISABLEVOMS: " + cometa_trodan_DISABLEVOMS
                        
                    + "\n\ntrodan_ENABLEINFRASTRUCTURE: " + "cometa"
                    + "\ntrodan_APPID: " + trodan_APPID
                    + "\ntrodan_LOGLEVEL: " + trodan_LOGLEVEL
                    + "\ntrodan_OUTPUT_PATH: " + trodan_OUTPUT_PATH
                    + "\ntrodan_SOFTWARE: " + trodan_SOFTWARE
                    + "\ntrodan_REPOSITORY: " + trodan_REPOSITORY
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
            }

            if (gridit_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[1]="gridit";
                 // Getting the TRODAN INFRASTRUCTURE from the portlet request for the GRIDIT VO
                String gridit_trodan_INFRASTRUCTURE = request.getParameter("gridit_trodan_INFRASTRUCTURE");
                // Getting the TRODAN VONAME from the portlet request for the GRIDIT VO
                String gridit_trodan_VONAME = request.getParameter("gridit_trodan_VONAME");
                // Getting the TRODAN TOPBDII from the portlet request for the GRIDIT VO
                String gridit_trodan_TOPBDII = request.getParameter("gridit_trodan_TOPBDII");
                // Getting the TRODAN WMS from the portlet request for the GRIDIT VO
                String[] gridit_trodan_WMS = request.getParameterValues("gridit_trodan_WMS");
                // Getting the TRODAN ETOKENSERVER from the portlet request for the GRIDIT VO
                String gridit_trodan_ETOKENSERVER = request.getParameter("gridit_trodan_ETOKENSERVER");
                // Getting the TRODAN MYPROXYSERVER from the portlet request for the GRIDIT VO
                String gridit_trodan_MYPROXYSERVER = request.getParameter("gridit_trodan_MYPROXYSERVER");
                // Getting the TRODAN PORT from the portlet request for the GRIDIT VO
                String gridit_trodan_PORT = request.getParameter("gridit_trodan_PORT");
                // Getting the TRODAN METADATASERVER from the portlet request for the GRIDIT VO
                String gridit_trodan_METADATASERVER = request.getParameter("gridit_trodan_METADATASERVER");
                // Getting the TRODAN METADATAPORT from the portlet request for the GRIDIT VO
                String gridit_trodan_METADATAPORT = request.getParameter("gridit_trodan_METADATAPORT");
                // Getting the TRODAN ROBOTID from the portlet request for the GRIDIT VO
                String gridit_trodan_ROBOTID = request.getParameter("gridit_trodan_ROBOTID");
                // Getting the TRODAN ROLE from the portlet request for the GRIDIT VO
                String gridit_trodan_ROLE = request.getParameter("gridit_trodan_ROLE");
                // Getting the TRODAN OPTIONS from the portlet request for the GRIDIT VO
                String[] gridit_trodan_OPTIONS = request.getParameterValues("gridit_trodan_OPTIONS");

                String gridit_trodan_RENEWAL = "";
                String gridit_trodan_DISABLEVOMS = "";

                if (gridit_trodan_OPTIONS == null){
                    gridit_trodan_RENEWAL = "checked";
                    gridit_trodan_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(gridit_trodan_OPTIONS);
                    // Getting the TRODAN RENEWAL from the portlet preferences for the GRIDIT VO
                    gridit_trodan_RENEWAL = Arrays.binarySearch(gridit_trodan_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Getting the TRODAN DISABLEVOMS from the portlet preferences for the GRIDIT VO
                    gridit_trodan_DISABLEVOMS = Arrays.binarySearch(gridit_trodan_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < gridit_trodan_WMS.length; i++)
                    if ( gridit_trodan_WMS[i]!=null && (!gridit_trodan_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] gridit_trodan_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    gridit_trodan_WMS_trimmed[i]=gridit_trodan_WMS[i].trim();
                    log.info ("\n\nCOMETA [" + i + "] WMS=[" + gridit_trodan_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("gridit_trodan_INFRASTRUCTURE", gridit_trodan_INFRASTRUCTURE.trim());
                portletPreferences.setValue("gridit_trodan_VONAME", gridit_trodan_VONAME.trim());
                portletPreferences.setValue("gridit_trodan_TOPBDII", gridit_trodan_TOPBDII.trim());
                portletPreferences.setValues("gridit_trodan_WMS", gridit_trodan_WMS_trimmed);
                portletPreferences.setValue("gridit_trodan_ETOKENSERVER", gridit_trodan_ETOKENSERVER.trim());
                portletPreferences.setValue("gridit_trodan_MYPROXYSERVER", gridit_trodan_MYPROXYSERVER.trim());
                portletPreferences.setValue("gridit_trodan_PORT", gridit_trodan_PORT.trim());
                portletPreferences.setValue("gridit_trodan_METADATASERVER", gridit_trodan_METADATASERVER.trim());
                portletPreferences.setValue("gridit_trodan_METADATAPORT", gridit_trodan_METADATAPORT.trim());
                portletPreferences.setValue("gridit_trodan_ROBOTID", gridit_trodan_ROBOTID.trim());
                portletPreferences.setValue("gridit_trodan_ROLE", gridit_trodan_ROLE.trim());
                portletPreferences.setValue("gridit_trodan_RENEWAL", gridit_trodan_RENEWAL);
                portletPreferences.setValue("gridit_trodan_DISABLEVOMS", gridit_trodan_DISABLEVOMS);                
                
                portletPreferences.setValue("trodan_APPID", trodan_APPID.trim());
                portletPreferences.setValue("trodan_LOGLEVEL", trodan_LOGLEVEL.trim());
                portletPreferences.setValue("trodan_OUTPUT_PATH", trodan_OUTPUT_PATH.trim());
                portletPreferences.setValue("trodan_REPOSITORY", trodan_REPOSITORY.trim());
                portletPreferences.setValue("trodan_SOFTWARE", trodan_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                    + "\n- Storing the TRODAN portlet preferences ..."
                    + "\ngridit_trodan_INFRASTRUCTURE: " + gridit_trodan_INFRASTRUCTURE
                    + "\ngridit_trodan_VONAME: " + gridit_trodan_VONAME
                    + "\ngridit_trodan_TOPBDII: " + gridit_trodan_TOPBDII                    
                    + "\ngridit_trodan_ETOKENSERVER: " + gridit_trodan_ETOKENSERVER
                    + "\ngridit_trodan_MYPROXYSERVER: " + gridit_trodan_MYPROXYSERVER
                    + "\ngridit_trodan_PORT: " + gridit_trodan_PORT
                    + "\ngridit_trodan_METADATASERVER: " + gridit_trodan_METADATASERVER
                    + "\ngridit_trodan_METADATAPORT: " + gridit_trodan_METADATAPORT                        
                    + "\ngridit_trodan_ROBOTID: " + gridit_trodan_ROBOTID
                    + "\ngridit_trodan_ROLE: " + gridit_trodan_ROLE
                    + "\ngridit_trodan_RENEWAL: " + gridit_trodan_RENEWAL
                    + "\ngridit_trodan_DISABLEVOMS: " + gridit_trodan_DISABLEVOMS
                        
                    + "\n\ntrodan_ENABLEINFRASTRUCTURE: " + "gridit"
                    + "\ntrodan_APPID: " + trodan_APPID
                    + "\ntrodan_LOGLEVEL: " + trodan_LOGLEVEL
                    + "\ntrodan_OUTPUT_PATH: " + trodan_OUTPUT_PATH
                    + "\ntrodan_REPOSITORY: " + trodan_REPOSITORY
                    + "\ntrodan_SOFTWARE: " + trodan_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
            }

            if (eumed_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[2]="eumed";
                // Getting the TRODAN INFRASTRUCTURE from the portlet request for the EUMED VO
                String eumed_trodan_INFRASTRUCTURE = request.getParameter("eumed_trodan_INFRASTRUCTURE");
                // Getting the TRODAN VONAME from the portlet request for the EUMED VO
                String eumed_trodan_VONAME = request.getParameter("eumed_trodan_VONAME");
                // Getting the TRODAN TOPBDII from the portlet request for the EUMED VO
                String eumed_trodan_TOPBDII = request.getParameter("eumed_trodan_TOPBDII");
                // Getting the TRODAN WMS from the portlet request for the EUMED VO
                String[] eumed_trodan_WMS = request.getParameterValues("eumed_trodan_WMS");
                // Getting the TRODAN ETOKENSERVER from the portlet request for the EUMED VO
                String eumed_trodan_ETOKENSERVER = request.getParameter("eumed_trodan_ETOKENSERVER");
                // Getting the TRODAN MYPROXYSERVER from the portlet request for the EUMED VO
                String eumed_trodan_MYPROXYSERVER = request.getParameter("eumed_trodan_MYPROXYSERVER");
                // Getting the TRODAN PORT from the portlet request for the EUMED VO
                String eumed_trodan_PORT = request.getParameter("eumed_trodan_PORT");
                // Getting the TRODAN METADATASERVER from the portlet request for the EUMED VO
                String eumed_trodan_METADATASERVER = request.getParameter("eumed_trodan_METADATASERVER");
                // Getting the TRODAN METADATAPORT from the portlet request for the EUMED VO
                String eumed_trodan_METADATAPORT = request.getParameter("eumed_trodan_METADATAPORT");
                // Getting the TRODAN ROBOTID from the portlet request for the EUMED VO
                String eumed_trodan_ROBOTID = request.getParameter("eumed_trodan_ROBOTID");
                // Getting the TRODAN ROLE from the portlet request for the EUMED VO
                String eumed_trodan_ROLE = request.getParameter("eumed_trodan_ROLE");
                // Getting the TRODAN OPTIONS from the portlet request for the EUMED VO
                String[] eumed_trodan_OPTIONS = request.getParameterValues("eumed_trodan_OPTIONS");

                String eumed_trodan_RENEWAL = "";
                String eumed_trodan_DISABLEVOMS = "";

                if (eumed_trodan_OPTIONS == null){
                    eumed_trodan_RENEWAL = "checked";
                    eumed_trodan_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(eumed_trodan_OPTIONS);
                    // Getting the TRODAN RENEWAL from the portlet preferences for the EUMED VO
                    eumed_trodan_RENEWAL = Arrays.binarySearch(eumed_trodan_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Getting the TRODAN DISABLEVOMS from the portlet preferences for the GRIDIT VO
                    eumed_trodan_DISABLEVOMS = Arrays.binarySearch(eumed_trodan_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < eumed_trodan_WMS.length; i++)
                    if ( eumed_trodan_WMS[i]!=null && (!eumed_trodan_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] eumed_trodan_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    eumed_trodan_WMS_trimmed[i]=eumed_trodan_WMS[i].trim();
                    log.info ("\n\nEUMED [" + i + "] WMS=[" + eumed_trodan_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("eumed_trodan_INFRASTRUCTURE", eumed_trodan_INFRASTRUCTURE.trim());
                portletPreferences.setValue("eumed_trodan_VONAME", eumed_trodan_VONAME.trim());
                portletPreferences.setValue("eumed_trodan_TOPBDII", eumed_trodan_TOPBDII.trim());
                portletPreferences.setValues("eumed_trodan_WMS", eumed_trodan_WMS_trimmed);
                portletPreferences.setValue("eumed_trodan_ETOKENSERVER", eumed_trodan_ETOKENSERVER.trim());
                portletPreferences.setValue("eumed_trodan_MYPROXYSERVER", eumed_trodan_MYPROXYSERVER.trim());
                portletPreferences.setValue("eumed_trodan_PORT", eumed_trodan_PORT.trim());
                portletPreferences.setValue("eumed_trodan_METADATASERVER", eumed_trodan_METADATASERVER.trim());
                portletPreferences.setValue("eumed_trodan_METADATAPORT", eumed_trodan_METADATAPORT.trim());
                portletPreferences.setValue("eumed_trodan_ROBOTID", eumed_trodan_ROBOTID.trim());
                portletPreferences.setValue("eumed_trodan_ROLE", eumed_trodan_ROLE.trim());
                portletPreferences.setValue("eumed_trodan_RENEWAL", eumed_trodan_RENEWAL);
                portletPreferences.setValue("eumed_trodan_DISABLEVOMS", eumed_trodan_DISABLEVOMS); 
                
                portletPreferences.setValue("trodan_APPID", trodan_APPID.trim());
                portletPreferences.setValue("trodan_LOGLEVEL", trodan_LOGLEVEL.trim());
                portletPreferences.setValue("trodan_OUTPATH_PATH", trodan_OUTPUT_PATH.trim());
                portletPreferences.setValue("trodan_REPOSITORY", trodan_REPOSITORY.trim());
                portletPreferences.setValue("trodan_SOFTWARE", trodan_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                    + "\n- Storing the TRODAN portlet preferences ..."                    
                    + "\n\neumed_trodan_INFRASTRUCTURE: " + eumed_trodan_INFRASTRUCTURE
                    + "\neumed_trodan_VONAME: " + eumed_trodan_VONAME
                    + "\neumed_trodan_TOPBDII: " + eumed_trodan_TOPBDII                    
                    + "\neumed_trodan_ETOKENSERVER: " + eumed_trodan_ETOKENSERVER
                    + "\neumed_trodan_MYPROXYSERVER: " + eumed_trodan_MYPROXYSERVER
                    + "\neumed_trodan_PORT: " + eumed_trodan_PORT
                    + "\neumed_trodan_METADATASERVER: " + eumed_trodan_METADATASERVER
                    + "\neumed_trodan_METADATAPORT: " + eumed_trodan_METADATAPORT
                    + "\neumed_trodan_ROBOTID: " + eumed_trodan_ROBOTID
                    + "\neumed_trodan_ROLE: " + eumed_trodan_ROLE
                    + "\neumed_trodan_RENEWAL: " + eumed_trodan_RENEWAL
                    + "\neumed_trodan_DISABLEVOMS: " + eumed_trodan_DISABLEVOMS

                    + "\n\ntrodan_ENABLEINFRASTRUCTURE: " + "eumed"
                    + "\ntrodan_APPID: " + trodan_APPID
                    + "\ntrodan_LOGLEVEL: " + trodan_LOGLEVEL
                    + "\ntrodan_OUTPUT_PATH: " + trodan_OUTPUT_PATH
                    + "\ntrodan_REPOSITORY: " + trodan_REPOSITORY
                    + "\ntrodan_SOFTWARE: " + trodan_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
            }

            if (biomed_trodan_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[3]="biomed";
                // Getting the TRODAN INFRASTRUCTURE from the portlet request for the BIOMED VO
                String biomed_trodan_INFRASTRUCTURE = request.getParameter("biomed_trodan_INFRASTRUCTURE");
                // Getting the TRODAN VONAME from the portlet request for the BIOMED VO
                String biomed_trodan_VONAME = request.getParameter("biomed_trodan_VONAME");
                // Getting the TRODAN TOPBDII from the portlet request for the BIOMED VO
                String biomed_trodan_TOPBDII = request.getParameter("biomed_trodan_TOPBDII");
                // Getting the TRODAN WMS from the portlet request for the BIOMED VO
                String[] biomed_trodan_WMS = request.getParameterValues("biomed_trodan_WMS");
                // Getting the TRODAN ETOKENSERVER from the portlet request for the BIOMED VO
                String biomed_trodan_ETOKENSERVER = request.getParameter("biomed_trodan_ETOKENSERVER");
                // Getting the TRODAN MYPROXYSERVER from the portlet request for the BIOMED VO
                String biomed_trodan_MYPROXYSERVER = request.getParameter("biomed_trodan_MYPROXYSERVER");
                // Getting the TRODAN PORT from the portlet request for the BIOMED VO
                String biomed_trodan_PORT = request.getParameter("biomed_trodan_PORT");
                // Getting the TRODAN METADATASERVER from the portlet request for the BIOMED VO
                String biomed_trodan_METADATASERVER = request.getParameter("biomed_trodan_METADATASERVER");
                // Getting the TRODAN METADATAPORT from the portlet request for the BIOMED VO
                String biomed_trodan_METADATAPORT = request.getParameter("biomed_trodan_METADATAPORT");
                // Getting the TRODAN ROBOTID from the portlet request for the BIOMED VO
                String biomed_trodan_ROBOTID = request.getParameter("biomed_trodan_ROBOTID");
                // Getting the TRODAN ROLE from the portlet request for the BIOMED VO
                String biomed_trodan_ROLE = request.getParameter("biomed_trodan_ROLE");
                // Getting the TRODAN OPTIONS from the portlet request for the BIOMED VO
                String[] biomed_trodan_OPTIONS = request.getParameterValues("biomed_trodan_OPTIONS");

                String biomed_trodan_RENEWAL = "";
                String biomed_trodan_DISABLEVOMS = "";

                if (biomed_trodan_OPTIONS == null){
                    biomed_trodan_RENEWAL = "checked";
                    biomed_trodan_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(biomed_trodan_OPTIONS);
                    // Get the TRODAN RENEWAL from the portlet preferences for the BIOMED VO
                    biomed_trodan_RENEWAL = Arrays.binarySearch(biomed_trodan_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the TRODAN DISABLEVOMS from the portlet preferences for the BIOMED VO
                    biomed_trodan_DISABLEVOMS = Arrays.binarySearch(biomed_trodan_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < biomed_trodan_WMS.length; i++)
                    if ( biomed_trodan_WMS[i]!=null && (!biomed_trodan_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] biomed_trodan_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    biomed_trodan_WMS_trimmed[i]=biomed_trodan_WMS[i].trim();
                    log.info ("\n\nBIOMED [" + i + "] WMS=[" + biomed_trodan_WMS_trimmed[i] + "]");
                }

                // Set the portlet preferences
                portletPreferences.setValue("biomed_trodan_INFRASTRUCTURE", biomed_trodan_INFRASTRUCTURE.trim());
                portletPreferences.setValue("biomed_trodan_VONAME", biomed_trodan_VONAME.trim());
                portletPreferences.setValue("biomed_trodan_TOPBDII", biomed_trodan_TOPBDII.trim());
                portletPreferences.setValues("biomed_trodan_WMS", biomed_trodan_WMS_trimmed);
                portletPreferences.setValue("biomed_trodan_ETOKENSERVER", biomed_trodan_ETOKENSERVER.trim());
                portletPreferences.setValue("biomed_trodan_MYPROXYSERVER", biomed_trodan_MYPROXYSERVER.trim());
                portletPreferences.setValue("biomed_trodan_PORT", biomed_trodan_PORT.trim());
                portletPreferences.setValue("biomed_trodan_METADATASERVER", biomed_trodan_METADATASERVER.trim());
                portletPreferences.setValue("biomed_trodan_METADATAPORT", biomed_trodan_METADATAPORT.trim());
                portletPreferences.setValue("biomed_trodan_ROBOTID", biomed_trodan_ROBOTID.trim());
                portletPreferences.setValue("biomed_trodan_ROLE", biomed_trodan_ROLE.trim());
                portletPreferences.setValue("biomed_trodan_RENEWAL", biomed_trodan_RENEWAL);
                portletPreferences.setValue("biomed_trodan_DISABLEVOMS", biomed_trodan_DISABLEVOMS);
                
                portletPreferences.setValue("trodan_APPID", trodan_APPID.trim());
                portletPreferences.setValue("trodan_LOGLEVEL", trodan_LOGLEVEL.trim());
                portletPreferences.setValue("trodan_OUTPUT_PATH", trodan_OUTPUT_PATH.trim());
                portletPreferences.setValue("trodan_REPOSITORY", trodan_REPOSITORY.trim());
                portletPreferences.setValue("trodan_SOFTWARE", trodan_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                    + "\n- Storing the TRODAN portlet preferences ..."                    
                    + "\n\nbiomed_trodan_INFRASTRUCTURE: " + biomed_trodan_INFRASTRUCTURE
                    + "\nbiomed_trodan_VONAME: " + biomed_trodan_VONAME
                    + "\nbiomed_trodan_TOPBDII: " + biomed_trodan_TOPBDII                    
                    + "\nbiomed_trodan_ETOKENSERVER: " + biomed_trodan_ETOKENSERVER
                    + "\nbiomed_trodan_MYPROXYSERVER: " + biomed_trodan_MYPROXYSERVER
                    + "\nbiomed_trodan_PORT: " + biomed_trodan_PORT
                    + "\nbiomed_trodan_METADATASERVER: " + biomed_trodan_METADATASERVER
                    + "\nbiomed_trodan_METADATAPORT: " + biomed_trodan_METADATAPORT
                    + "\nbiomed_trodan_ROBOTID: " + biomed_trodan_ROBOTID
                    + "\nbiomed_trodan_ROLE: " + biomed_trodan_ROLE
                    + "\nbiomed_trodan_RENEWAL: " + biomed_trodan_RENEWAL
                    + "\nbiomed_trodan_DISABLEVOMS: " + biomed_trodan_DISABLEVOMS

                    + "\n\ntrodan_ENABLEINFRASTRUCTURE: " + "biomed"
                    + "\ntrodan_APPID: " + trodan_APPID
                    + "\ntrodan_LOGLEVEL: " + trodan_LOGLEVEL
                    + "\ntrodan_OUTPUT_PATH: " + trodan_OUTPUT_PATH
                    + "\ntrodan_REPOSITORY: " + trodan_REPOSITORY
                    + "\ntrodan_SOFTWARE: " + trodan_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
            }
            
            for (int i=0; i<infras.length; i++)
            log.info("\n - Infrastructure Enabled = " + infras[i]);
            portletPreferences.setValues("trodan_ENABLEINFRASTRUCTURE", infras);
            portletPreferences.setValue("cometa_trodan_ENABLEINFRASTRUCTURE",infras[0]);            
            portletPreferences.setValue("gridit_trodan_ENABLEINFRASTRUCTURE",infras[1]);            
            portletPreferences.setValue("eumed_trodan_ENABLEINFRASTRUCTURE",infras[2]);
            portletPreferences.setValue("biomed_trodan_ENABLEINFRASTRUCTURE",infras[3]);            

            portletPreferences.store();
            response.setPortletMode(PortletMode.VIEW);
        } // end PROCESS ACTION [ CONFIG_TRODAN_PORTLET ]
        

        if (action.equals("SUBMIT_TRODAN_PORTLET")) {
            log.info("\nPROCESS ACTION => " + action);            
            InfrastructureInfo infrastructures[] = new InfrastructureInfo[4];
            int MAX=0;
            
            // Getting the TRODAN APPID from the portlet preferences
            String trodan_APPID = portletPreferences.getValue("trodan_APPID", "N/A");
            // Get the LOGLEVEL from the portlet preferences
            String trodan_LOGLEVEL = portletPreferences.getValue("trodan_LOGLEVEL", "INFO");
            // Getting the TRODAN OUTPUT_PATH from the portlet preferences
            String trodan_OUTPUT_PATH = portletPreferences.getValue("trodan_OUTPUT_PATH", "/tmp");
            // Getting the TRODAN METADATAREPOSITORY from the portlet preferences
            String trodan_REPOSITORY = portletPreferences.getValue("trodan_REPOSITORY", "N/A");
            // Getting the TRODAN SOFTWARE from the portlet preferences
            String trodan_SOFTWARE = portletPreferences.getValue("trodan_SOFTWARE", "N/A");
            // Getting the TRACKING_DB_HOSTNAME from the portlet request
            String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
            // Getting the TRACKING_DB_USERNAME from the portlet request
            String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
            // Getting the TRACKING_DB_PASSWORD from the portlet request
            String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD","N/A");
            // Getting the SMTP_HOST from the portlet request
            String SMTP_HOST = portletPreferences.getValue("SMTP_HOST","N/A");
            // Getting the SENDER_MAIL from the portlet request
            String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL","N/A");  
            // Getting all selected stations from the portlet request
            String [] selectedStations = request.getParameterValues("selectedStations");
            
            String cometa_trodan_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("cometa_trodan_ENABLEINFRASTRUCTURE","null");
            String gridit_trodan_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("gridit_trodan_ENABLEINFRASTRUCTURE","null");
            String eumed_trodan_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("eumed_trodan_ENABLEINFRASTRUCTURE","null");
            String biomed_trodan_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("biomed_trodan_ENABLEINFRASTRUCTURE","null");
            
            String eumed_trodan_METADATASERVER = "";
            String eumed_trodan_METADATAPORT = "";
            
            if (cometa_trodan_ENABLEINFRASTRUCTURE != null &&
                cometa_trodan_ENABLEINFRASTRUCTURE.equals("cometa"))
            {
                MAX++;
                // Getting the TRODAN VONAME from the portlet preferences for the COMETA VO
                String cometa_trodan_INFRASTRUCTURE = portletPreferences.getValue("cometa_trodan_INFRASTRUCTURE", "N/A");
                // Getting the TRODAN VONAME from the portlet preferences for the COMETA VO
                String cometa_trodan_VONAME = portletPreferences.getValue("cometa_trodan_VONAME", "N/A");
                // Getting the TRODAN TOPPBDII from the portlet preferences for the COMETA VO
                String cometa_trodan_TOPBDII = portletPreferences.getValue("cometa_trodan_TOPBDII", "N/A");
                // Getting the TRODAN WMS from the portlet preferences for the COMETA VO                
                String[] cometa_trodan_WMS = portletPreferences.getValues("cometa_trodan_WMS", new String[5]);
                // Getting the TRODAN ETOKENSERVER from the portlet preferences for the COMETA VO
                String cometa_trodan_ETOKENSERVER = portletPreferences.getValue("cometa_trodan_ETOKENSERVER", "N/A");
                // Getting the TRODAN MYPROXYSERVER from the portlet preferences for the COMETA VO
                String cometa_trodan_MYPROXYSERVER = portletPreferences.getValue("cometa_trodan_MYPROXYSERVER", "N/A");
                // Getting the TRODAN PORT from the portlet preferences for the COMETA VO
                String cometa_trodan_PORT = portletPreferences.getValue("cometa_trodan_PORT", "N/A");
                // Getting the TRODAN METADATASERVER from the portlet preferences for the COMETA VO
                String cometa_trodan_METADATASERVER = portletPreferences.getValue("cometa_trodan_METADATASERVER", "N/A");
                // Getting the TRODAN METADATAPORT from the portlet preferences for the COMETA VO
                String cometa_trodan_METADATAPORT = portletPreferences.getValue("cometa_trodan_METADATAPORT", "N/A");
                // Getting the TRODAN ROBOTID from the portlet preferences for the COMETA VO
                String cometa_trodan_ROBOTID = portletPreferences.getValue("cometa_trodan_ROBOTID", "N/A");
                // Getting the TRODAN ROLE from the portlet preferences for the COMETA VO
                String cometa_trodan_ROLE = portletPreferences.getValue("cometa_trodan_ROLE", "N/A");
                // Getting the TRODAN RENEWAL from the portlet preferences for the COMETA VO
                String cometa_trodan_RENEWAL = portletPreferences.getValue("cometa_trodan_RENEWAL", "checked");
                // Getting the TRODAN DISABLEVOMS from the portlet preferences for the COMETA VO
                String cometa_trodan_DISABLEVOMS = portletPreferences.getValue("cometa_trodan_DISABLEVOMS", "unchecked");
                
                log.info("\n- Getting the IOR portlet preferences ..."
                    + "\ncometa_trodan_INFRASTRUCTURE: " + cometa_trodan_INFRASTRUCTURE
                    + "\ncometa_trodan_VONAME: " + cometa_trodan_VONAME
                    + "\ncometa_trodan_TOPBDII: " + cometa_trodan_TOPBDII                    
                    + "\ncometa_trodan_ETOKENSERVER: " + cometa_trodan_ETOKENSERVER
                    + "\ncometa_trodan_MYPROXYSERVER: " + cometa_trodan_MYPROXYSERVER
                    + "\ncometa_trodan_PORT: " + cometa_trodan_PORT
                    + "\ncometa_trodan_METADATASERVER: " + cometa_trodan_METADATASERVER
                    + "\ncometa_trodan_METADATAPORT: " + cometa_trodan_METADATAPORT
                    + "\ncometa_trodan_ROBOTID: " + cometa_trodan_ROBOTID
                    + "\ncometa_trodan_ROLE: " + cometa_trodan_ROLE
                    + "\ncometa_trodan_RENEWAL: " + cometa_trodan_RENEWAL
                    + "\ncometa_trodan_DISABLEVOMS: " + cometa_trodan_DISABLEVOMS
                   
                    + "\n\ntrodan_ENABLEINFRASTRUCTURE: " + cometa_trodan_ENABLEINFRASTRUCTURE
                    + "\ntrodan_APPID: " + trodan_APPID
                    + "\ntrodan_LOGLEVEL: " + trodan_LOGLEVEL
                    + "\ntrodan_OUTPUT_PATH: " + trodan_OUTPUT_PATH
                    + "\ntrodan_REPOSITORY: " + trodan_REPOSITORY
                    + "\ntrodan_SOFTWARE: " + trodan_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "COMETA" Infrastructure
                int nmax=0;
                for (int i = 0; i < cometa_trodan_WMS.length; i++)
                    if ((cometa_trodan_WMS[i]!=null) && (!cometa_trodan_WMS[i].equals("N/A"))) nmax++;

                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (cometa_trodan_WMS[i]!=null) {
                    wmsList[i]=cometa_trodan_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to COMETA ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[0] = new InfrastructureInfo(
                    cometa_trodan_VONAME,
                    cometa_trodan_TOPBDII,
                    wmsList,
                    cometa_trodan_ETOKENSERVER,
                    cometa_trodan_PORT,
                    cometa_trodan_ROBOTID,
                    cometa_trodan_VONAME,
                    cometa_trodan_ROLE,
                    "VO-" + cometa_trodan_VONAME + "-" + trodan_SOFTWARE);
            }
                        
            if (gridit_trodan_ENABLEINFRASTRUCTURE != null &&
                gridit_trodan_ENABLEINFRASTRUCTURE.equals("gridit"))
            {
                MAX++;
                // Getting the TRODANVONAME from the portlet preferences for the GRIDIT VO
                String gridit_trodan_INFRASTRUCTURE = portletPreferences.getValue("gridit_trodan_INFRASTRUCTURE", "N/A");
                // Getting the TRODAN VONAME from the portlet preferences for the GRIDIT VO
                String gridit_trodan_VONAME = portletPreferences.getValue("gridit_trodan_VONAME", "N/A");
                // Getting the TRODAN TOPPBDII from the portlet preferences for the GRIDIT VO
                String gridit_trodan_TOPBDII = portletPreferences.getValue("gridit_trodan_TOPBDII", "N/A");
                // Getting the TRODAN WMS from the portlet preferences for the GRIDIT VO                
                String[] gridit_trodan_WMS = portletPreferences.getValues("gridit_trodan_WMS", new String[5]);
                // Getting the TRODAN ETOKENSERVER from the portlet preferences for the GRIDIT VO
                String gridit_trodan_ETOKENSERVER = portletPreferences.getValue("gridit_trodan_ETOKENSERVER", "N/A");
                // Getting the TRODAN MYPROXYSERVER from the portlet preferences for the GRIDIT VO
                String gridit_trodan_MYPROXYSERVER = portletPreferences.getValue("gridit_trodan_MYPROXYSERVER", "N/A");
                // Getting the TRODAN PORT from the portlet preferences for the GRIDIT VO
                String gridit_trodan_PORT = portletPreferences.getValue("gridit_trodan_PORT", "N/A");
                // Getting the TRODAN METADATASERVER from the portlet preferences for the GRIDIT VO
                String gridit_trodan_METADATASERVER = portletPreferences.getValue("gridit_trodan_METADATASERVER", "N/A");
                // Getting the TRODAN METADATAPORT from the portlet preferences for the GRIDIT VO
                String gridit_trodan_METADATAPORT = portletPreferences.getValue("gridit_trodan_METADATAPORT", "N/A");
                // Getting the TRODAN ROBOTID from the portlet preferences for the GRIDIT VO
                String gridit_trodan_ROBOTID = portletPreferences.getValue("gridit_trodan_ROBOTID", "N/A");
                // Getting the TRODAN ROLE from the portlet preferences for the GRIDIT VO
                String gridit_trodan_ROLE = portletPreferences.getValue("gridit_trodan_ROLE", "N/A");
                // Getting the TRODAN RENEWAL from the portlet preferences for the GRIDIT VO
                String gridit_trodan_RENEWAL = portletPreferences.getValue("gridit_trodan_RENEWAL", "checked");
                // Getting the TRODAN DISABLEVOMS from the portlet preferences for the GRIDIT VO
                String gridit_trodan_DISABLEVOMS = portletPreferences.getValue("gridit_trodan_DISABLEVOMS", "unchecked");
                
                log.info("\n- Getting the TRODAN portlet preferences ..."
                    + "\ngridit_trodan_INFRASTRUCTURE: " + gridit_trodan_INFRASTRUCTURE
                    + "\ngridit_trodan_VONAME: " + gridit_trodan_VONAME
                    + "\ngridit_trodan_TOPBDII: " + gridit_trodan_TOPBDII                    
                    + "\ngridit_trodan_ETOKENSERVER: " + gridit_trodan_ETOKENSERVER
                    + "\ngridit_trodan_MYPROXYSERVER: " + gridit_trodan_MYPROXYSERVER
                    + "\ngridit_trodan_PORT: " + gridit_trodan_PORT
                    + "\ngridit_trodan_METADATASERVER: " + gridit_trodan_METADATASERVER
                    + "\ngridit_trodan_METADATAPORT: " + gridit_trodan_METADATAPORT
                    + "\ngridit_trodan_ROBOTID: " + gridit_trodan_ROBOTID
                    + "\ngridit_trodan_ROLE: " + gridit_trodan_ROLE
                    + "\ngridit_trodan_RENEWAL: " + gridit_trodan_RENEWAL
                    + "\ngridit_trodan_DISABLEVOMS: " + gridit_trodan_DISABLEVOMS
                   
                    + "\n\ntrodan_ENABLEINFRASTRUCTURE: " + gridit_trodan_ENABLEINFRASTRUCTURE
                    + "\ntrodan_APPID: " + trodan_APPID
                    + "\ntrodan_LOGLEVEL: " + trodan_LOGLEVEL
                    + "\ntrodan_OUTPUT_PATH: " + trodan_OUTPUT_PATH
                    + "\ntrodan_REPOSITORY: " + trodan_REPOSITORY
                    + "\ntrodan_SOFTWARE: " + trodan_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "GRIDIT" Infrastructure
                int nmax=0;
                for (int i = 0; i < gridit_trodan_WMS.length; i++)
                    if ((gridit_trodan_WMS[i]!=null) && (!gridit_trodan_WMS[i].equals("N/A"))) nmax++;

                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (gridit_trodan_WMS[i]!=null) {
                    wmsList[i]=gridit_trodan_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to GRIDIT ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[1] = new InfrastructureInfo(
                    gridit_trodan_VONAME,
                    gridit_trodan_TOPBDII,
                    wmsList,
                    gridit_trodan_ETOKENSERVER,
                    gridit_trodan_PORT,
                    gridit_trodan_ROBOTID,
                    gridit_trodan_VONAME,
                    gridit_trodan_ROLE,
                    "VO-" + gridit_trodan_VONAME + "-" + trodan_SOFTWARE);
            }
            
            if (eumed_trodan_ENABLEINFRASTRUCTURE != null &&
                eumed_trodan_ENABLEINFRASTRUCTURE.equals("eumed"))
            {
                MAX++;
                // Getting the TRODAN VONAME from the portlet preferences for the EUMED VO
                String eumed_trodan_INFRASTRUCTURE = portletPreferences.getValue("eumed_trodan_INFRASTRUCTURE", "N/A");
                // Getting the TRODAN VONAME from the portlet preferences for the EUMED VO
                String eumed_trodan_VONAME = portletPreferences.getValue("eumed_trodan_VONAME", "N/A");
                // Getting the TRODAN TOPPBDII from the portlet preferences for the EUMED VO
                String eumed_trodan_TOPBDII = portletPreferences.getValue("eumed_trodan_TOPBDII", "N/A");
                // Getting the TRODAN WMS from the portlet preferences for the EUMED VO
                String[] eumed_trodan_WMS = portletPreferences.getValues("eumed_trodan_WMS", new String[5]);
                // Getting the TRODAN ETOKENSERVER from the portlet preferences for the EUMED VO
                String eumed_trodan_ETOKENSERVER = portletPreferences.getValue("eumed_trodan_ETOKENSERVER", "N/A");
                // Getting the TRODAN MYPROXYSERVER from the portlet preferences for the EUMED VO
                String eumed_trodan_MYPROXYSERVER = portletPreferences.getValue("eumed_trodan_MYPROXYSERVER", "N/A");
                // Getting the TRODAN PORT from the portlet preferences for the EUMED VO
                String eumed_trodan_PORT = portletPreferences.getValue("eumed_trodan_PORT", "N/A");
                // Getting the TRODAN METADATASERVER from the portlet preferences for the EUMED VO
                eumed_trodan_METADATASERVER = portletPreferences.getValue("eumed_trodan_METADATASERVER", "N/A");
                // Getting the TRODAN METADATAPORT from the portlet preferences for the EUMED VO
                eumed_trodan_METADATAPORT = portletPreferences.getValue("eumed_trodan_METADATAPORT", "N/A");
                // Getting the TRODAN ROBOTID from the portlet preferences for the EUMED VO
                String eumed_trodan_ROBOTID = portletPreferences.getValue("eumed_trodan_ROBOTID", "N/A");
                // Getting the TRODAN ROLE from the portlet preferences for the EUMED VO
                String eumed_trodan_ROLE = portletPreferences.getValue("eumed_trodan_ROLE", "N/A");
                // Getting the TRODAN RENEWAL from the portlet preferences for the EUMED VO
                String eumed_trodan_RENEWAL = portletPreferences.getValue("eumed_trodan_RENEWAL", "checked");
                // Getting the TRODAN DISABLEVOMS from the portlet preferences for the EUMED VO
                String eumed_trodan_DISABLEVOMS = portletPreferences.getValue("eumed_trodan_DISABLEVOMS", "unchecked");
                
                log.info("\n- Getting the TRODAN portlet preferences ..."
                    + "\n\neumed_trodan_INFRASTRUCTURE: " + eumed_trodan_INFRASTRUCTURE
                    + "\neumed_trodan_VONAME: " + eumed_trodan_VONAME
                    + "\neumed_trodan_TOPBDII: " + eumed_trodan_TOPBDII                    
                    + "\neumed_trodan_ETOKENSERVER: " + eumed_trodan_ETOKENSERVER
                    + "\neumed_trodan_MYPROXYSERVER: " + eumed_trodan_MYPROXYSERVER
                    + "\neumed_trodan_PORT: " + eumed_trodan_PORT
                    + "\neumed_trodan_METADATASERVER: " + eumed_trodan_METADATASERVER
                    + "\neumed_trodan_METADATAPORT: " + eumed_trodan_METADATAPORT
                    + "\neumed_trodan_ROBOTID: " + eumed_trodan_ROBOTID
                    + "\neumed_trodan_ROLE: " + eumed_trodan_ROLE
                    + "\neumed_trodan_RENEWAL: " + eumed_trodan_RENEWAL
                    + "\neumed_trodan_DISABLEVOMS: " + eumed_trodan_DISABLEVOMS

                    + "\n\ntrodan_ENABLEINFRASTRUCTURE: " + eumed_trodan_ENABLEINFRASTRUCTURE
                    + "\ntrodan_APPID: " + trodan_APPID
                    + "\ntrodan_LOGLEVEL: " + trodan_LOGLEVEL
                    + "\ntrodan_OUTPUT_PATH: " + trodan_OUTPUT_PATH
                    + "\ntrodan_REPOSITORY: " + trodan_REPOSITORY
                    + "\ntrodan_SOFTWARE: " + trodan_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD                    
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "EUMED" Infrastructure
                int nmax=0;
                for (int i = 0; i < eumed_trodan_WMS.length; i++)
                    if ((eumed_trodan_WMS[i]!=null) && (!eumed_trodan_WMS[i].equals("N/A"))) nmax++;
                
                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (eumed_trodan_WMS[i]!=null) {
                    wmsList[i]=eumed_trodan_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to EUMED ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }                                

                infrastructures[2] = new InfrastructureInfo(
                    eumed_trodan_VONAME,
                    eumed_trodan_TOPBDII,
                    wmsList,
                    eumed_trodan_ETOKENSERVER,
                    eumed_trodan_PORT,
                    eumed_trodan_ROBOTID,
                    eumed_trodan_VONAME,
                    eumed_trodan_ROLE,
                    "VO-" + eumed_trodan_VONAME + "-" + trodan_SOFTWARE);
            }

            if (biomed_trodan_ENABLEINFRASTRUCTURE != null &&
                biomed_trodan_ENABLEINFRASTRUCTURE.equals("biomed")) 
            {
                MAX++;
                // Getting the TRODAN VONAME from the portlet preferences for the BIOMED VO
                String biomed_trodan_INFRASTRUCTURE = portletPreferences.getValue("biomed_trodan_INFRASTRUCTURE", "N/A");
                // Getting the TRODAN VONAME from the portlet preferences for the BIOMED VO
                String biomed_trodan_VONAME = portletPreferences.getValue("biomed_trodan_VONAME", "N/A");
                // Getting the TRODAN TOPPBDII from the portlet preferences for the BIOMED VO
                String biomed_trodan_TOPBDII = portletPreferences.getValue("biomed_trodan_TOPBDII", "N/A");
                // Getting the TRODAN WMS from the portlet preferences for the BIOMED VO
                String[] biomed_trodan_WMS = portletPreferences.getValues("biomed_trodan_WMS", new String[5]);
                // Getting the TRODAN ETOKENSERVER from the portlet preferences for the BIOMED VO
                String biomed_trodan_ETOKENSERVER = portletPreferences.getValue("biomed_trodan_ETOKENSERVER", "N/A");
                // Getting the TRODAN MYPROXYSERVER from the portlet preferences for the BIOMED VO
                String biomed_trodan_MYPROXYSERVER = portletPreferences.getValue("biomed_trodan_MYPROXYSERVER", "N/A");
                // Getting the TRODAN PORT from the portlet preferences for the BIOMED VO
                String biomed_trodan_PORT = portletPreferences.getValue("biomed_trodan_PORT", "N/A");
                // Getting the TRODAN METADATASERVER from the portlet preferences for the BIOMED VO
                String biomed_trodan_METADATASERVER = portletPreferences.getValue("biomed_trodan_METADATASERVER", "N/A");
                // Getting the TRODAN METADATAPORT from the portlet preferences for the BIOMED VO
                String biomed_trodan_METADATAPORT = portletPreferences.getValue("biomed_trodan_METADATAPORT", "N/A");
                // Getting the TRODAN ROBOTID from the portlet preferences for the BIOMED VO
                String biomed_trodan_ROBOTID = portletPreferences.getValue("biomed_trodan_ROBOTID", "N/A");
                // Getting the TRODAN ROLE from the portlet preferences for the BIOMED VO
                String biomed_trodan_ROLE = portletPreferences.getValue("biomed_trodan_ROLE", "N/A");
                // Getting the TRODAN RENEWAL from the portlet preferences for the BIOMED VO
                String biomed_trodan_RENEWAL = portletPreferences.getValue("biomed_trodan_RENEWAL", "checked");
                // Getting the TRODAN DISABLEVOMS from the portlet preferences for the BIOMED VO
                String biomed_trodan_DISABLEVOMS = portletPreferences.getValue("biomed_trodan_DISABLEVOMS", "unchecked");
                
                log.info("\n- Getting the TRODAN portlet preferences ..."
                + "\n\nbiomed_trodan_INFRASTRUCTURE: " + biomed_trodan_INFRASTRUCTURE
                + "\nbiomed_trodan_VONAME: " + biomed_trodan_VONAME
                + "\nbiomed_trodan_TOPBDII: " + biomed_trodan_TOPBDII                        
                + "\nbiomed_trodan_ETOKENSERVER: " + biomed_trodan_ETOKENSERVER
                + "\nbiomed_trodan_MYPROXYSERVER: " + biomed_trodan_MYPROXYSERVER
                + "\nbiomed_trodan_PORT: " + biomed_trodan_PORT
                + "\nbiomed_trodan_METADATASERVER: " + biomed_trodan_METADATASERVER
                + "\nbiomed_trodan_METADATAPORT: " + biomed_trodan_METADATAPORT                        
                + "\nbiomed_trodan_ROBOTID: " + biomed_trodan_ROBOTID
                + "\nbiomed_trodan_ROLE: " + biomed_trodan_ROLE
                + "\nbiomed_trodan_RENEWAL: " + biomed_trodan_RENEWAL
                + "\nbiomed_trodan_DISABLEVOMS: " + biomed_trodan_DISABLEVOMS

                + "\n\ntrodan_ENABLEINFRASTRUCTURE: " + biomed_trodan_ENABLEINFRASTRUCTURE
                + "\ntrodan_APPID: " + trodan_APPID
                + "\ntrodan_LOGLEVEL: " + trodan_LOGLEVEL
                + "\ntrodan_OUTPUT_PATH: " + trodan_OUTPUT_PATH                        
                + "\ntrodan_REPOSITORY: " + trodan_REPOSITORY
                + "\ntrodan_SOFTWARE: " + trodan_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "BIOMED" Infrastructure
                int nmax=0;
                for (int i = 0; i < biomed_trodan_WMS.length; i++)
                    if ((biomed_trodan_WMS[i]!=null) && (!biomed_trodan_WMS[i].equals("N/A"))) nmax++;
                
                String wmsList[] = new String [biomed_trodan_WMS.length];
                for (int i = 0; i < biomed_trodan_WMS.length; i++)
                {
                    if (biomed_trodan_WMS[i]!=null) {
                    wmsList[i]=biomed_trodan_WMS[i].trim();
                    log.info ("\n\nSubmitting for BIOMED [" + i + "] using WMS=[" + wmsList[i] + "]");
                    }
                }

                infrastructures[3] = new InfrastructureInfo(
                    biomed_trodan_VONAME,
                    biomed_trodan_TOPBDII,
                    wmsList,
                    biomed_trodan_ETOKENSERVER,
                    biomed_trodan_PORT,
                    biomed_trodan_ROBOTID,
                    biomed_trodan_VONAME,
                    biomed_trodan_ROLE,
                    "VO-" + biomed_trodan_VONAME + "-" + trodan_SOFTWARE);
            }

            String[] TRODAN_Parameters = new String [17];
            TRODAN_Parameters[0]=request.getParameter("trodan_desc");
            TRODAN_Parameters[1]=request.getParameter("trodan_CE");            
            TRODAN_Parameters[2]=request.getParameter("EnableAirTemperature");
            TRODAN_Parameters[3]=request.getParameter("EnableRelativeHumidity");
            TRODAN_Parameters[4]=request.getParameter("EnablePrecipitation");
            TRODAN_Parameters[5]=request.getParameter("EnableSoilTemperature");
            TRODAN_Parameters[6]=request.getParameter("EnableSolarRadiation");
            TRODAN_Parameters[7]=request.getParameter("EnableWindSpeed");
            TRODAN_Parameters[8]=request.getParameter("EnableBarPressure");                      
            TRODAN_Parameters[9]=request.getParameter("EnableWindDir");
            TRODAN_Parameters[10]=request.getParameter("EnableVW");
            TRODAN_Parameters[11]=request.getParameter("EnablePA_uS");
            TRODAN_Parameters[12]=request.getParameter("trodan_range_from");
            TRODAN_Parameters[13]=request.getParameter("trodan_range_to");
            TRODAN_Parameters[14]=request.getParameter("trodan_graph");
            TRODAN_Parameters[15]=request.getParameter("EnableNotification");
            TRODAN_Parameters[16]=request.getParameter("trodan_option");

            log.info ("\n\nParameters to visualize the TRODAN Data Repository. ");
            log.info("\n- Input Parameters: ");            
            log.info("\n- Description = " + TRODAN_Parameters[0]);
            log.info("\n- TRODAN_CE = " + TRODAN_Parameters[1]);            
            log.info("\n- Air Temperature = " + TRODAN_Parameters[2]);      // col 6
            log.info("\n- Relative Humidity = " + TRODAN_Parameters[3]);    // col 7
            log.info("\n- Precipitation = " + TRODAN_Parameters[4]);        // col 4
            log.info("\n- Soil Temperature = " + TRODAN_Parameters[5]);     // col 8
            log.info("\n- Solar Radiation = " + TRODAN_Parameters[6]);      // col 5
            log.info("\n- Wind Speed = " + TRODAN_Parameters[7]);           // col 9
            log.info("\n- Bar Pressure = " + TRODAN_Parameters[8]);            // col 11            
            log.info("\n- Wind Direction = " + TRODAN_Parameters[9]);      // col 10           
            log.info("\n- VW = " + TRODAN_Parameters[10]);                  // col 12
            log.info("\n- PA_uS = " + TRODAN_Parameters[11]);               // col 13
            log.info("\n\n- Date FROM = " + TRODAN_Parameters[12]);
            log.info("\n- Date TO = " + TRODAN_Parameters[13]);
            log.info("\n- Graph type = " + TRODAN_Parameters[14]);
            log.info("\n- Plot Option = " + TRODAN_Parameters[16]);
            log.info("\n- Enable Notification = " + TRODAN_Parameters[15]);
            
            // Retrieveing the list of CSV URLs for the selected STATIONS //
            List<String> TOTAL = new ArrayList<String>();
            Properties properties = new Properties();
            
            for(String station : selectedStations) 
            {                
                List<String> csv = new ArrayList<String>();
                List<String> stationID = new ArrayList<String>();
                                                                    
                properties.setProperty("ACTION", "getID");
                properties.setProperty("SERVER_HOST", eumed_trodan_METADATASERVER.trim());
                properties.setProperty("SERVER_PORT", eumed_trodan_METADATAPORT.trim());
                properties.setProperty("REPOSITORY", trodan_REPOSITORY);
                properties.setProperty("STATION", station);
                properties.setProperty("TYPE", "DataText");
                
                try {
                    stationID = doHTTPRequest(properties, trodan_LOGLEVEL.trim());
                    
                    properties.setProperty("ACTION", "getURL");
                    properties.setProperty("ID", stationID.get(0).replace("\"",""));
                    
                    csv = doHTTPRequest(properties, trodan_LOGLEVEL.trim());
                    log.info("Listing the available CSV repositories for the station [ "
                        + station
                        + " ]");
                    
                     for (int k=0; k<csv.size(); k++) {                         
                            TOTAL.add(csv.get(k));     
                            log.info("CSV URL = " + csv.get(k));
                     }
                    
                 } catch (Exception ex) {
                        Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
                 }                
            }
            
            // Store the list of Argument(s) in a file
            File TRODAN_Repository = new File ("/tmp");
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String timeStamp = dateFormat.format(Calendar.getInstance().getTime());
                        
            String Arguments_File = TRODAN_Repository +
                                        "/" + timeStamp +
                                        "_" + username +
                                        "_patterns.txt";
            
            storeFile (Arguments_File, TRODAN_Parameters, "Arguments");
            
            String Stations_File = TRODAN_Repository +
                                        "/" + timeStamp +
                                        "_" + username +
                                        "_stations.txt";
            
            storeFile (Stations_File, selectedStations, "Stations");
            
            String reposURL_File = TRODAN_Repository +
                                        "/" + timeStamp +
                                        "_" + username +
                                        "_urls.txt";
            
            storeFileFromList (reposURL_File, TOTAL);
            
            // Preparing to submit jobs in different grid infrastructure..
            //=============================================================
            // IMPORTANT: INSTANCIATE THE MultiInfrastructureJobSubmission
            //            CLASS USING THE EMPTY CONSTRUCTOR WHEN
            //            WHEN THE PORTLET IS DEPLOYED IN PRODUCTION!!!
            //=============================================================
            /*MultiInfrastructureJobSubmission TrodanMultiJobSubmission =
            new MultiInfrastructureJobSubmission(TRACKING_DB_HOSTNAME,
                                                 TRACKING_DB_USERNAME,
                                                 TRACKING_DB_PASSWORD);*/
            
            MultiInfrastructureJobSubmission TrodanMultiJobSubmission =
                new MultiInfrastructureJobSubmission();

            // Set the list of infrastructure(s) activated for the portlet           
            if (infrastructures[0]!=null) {
                if (trodan_LOGLEVEL.trim().equals("VERBOSE"))
                log.info("\n- Adding the COMETA Infrastructure.");
                 TrodanMultiJobSubmission.addInfrastructure(infrastructures[0]); 
            }            
            if (infrastructures[1]!=null) {
                if (trodan_LOGLEVEL.trim().equals("VERBOSE"))
                log.info("\n- Adding the GRIDIT Infrastructure.");
                 TrodanMultiJobSubmission.addInfrastructure(infrastructures[1]); 
            }
            if (infrastructures[2]!=null) {
                if (trodan_LOGLEVEL.trim().equals("VERBOSE"))
                log.info("\n- Adding the EUMED Infrastructure.");
                 TrodanMultiJobSubmission.addInfrastructure(infrastructures[2]);
            }
            if (infrastructures[3]!=null) {
                if (trodan_LOGLEVEL.trim().equals("VERBOSE"))
                log.info("\n- Adding the BIOMED Infrastructure.");
                 TrodanMultiJobSubmission.addInfrastructure(infrastructures[3]);
            }
            
            String TrodanFilesPath = getPortletContext().getRealPath("/") +
                                    "WEB-INF/config";                        
            
            // Set the Output path forresults            
            TrodanMultiJobSubmission.setOutputPath(trodan_OUTPUT_PATH);
                        
            // Set the StandardOutput for TRODAN
            TrodanMultiJobSubmission.setJobOutput("std.txt");

            // Set the StandardError for TRODAN
            TrodanMultiJobSubmission.setJobError("std.err");
            
            // Set the Executable for TRODAN
            TrodanMultiJobSubmission.setExecutable("drawTrodan_CVS.sh");
                        
            String Arguments = Stations_File + "," 
                            + reposURL_File + "," 
                            + Arguments_File + "," 
                            + TRODAN_Parameters[14] + "," 
                            + TRODAN_Parameters[12] + "," 
                            + TRODAN_Parameters[13] + "," 
                            + TRODAN_Parameters[16];
            
            // Set the list of Arguments for TRODAN
            TrodanMultiJobSubmission.setArguments(Arguments);
                
            String InputSandbox = TrodanFilesPath + "/drawTrodan_CVS.sh" + "," 
                                + Arguments_File + "," 
                                + Stations_File + "," 
                                + reposURL_File + "," 
                                + TrodanFilesPath + "/trodan_CSVdataParsing.py" + "," 
                                + TrodanFilesPath + "/sqlite.tar.gz";

            // Set InputSandbox files (string with comma separated list of file names)
            TrodanMultiJobSubmission.setInputFiles(InputSandbox);                                

            // OutputSandbox (string with comma separated list of file names)
            String README = "output.README";            
            String TrodanFiles="results.tar.gz";

            // Set the OutputSandbox files (string with comma separated list of file names)
            TrodanMultiJobSubmission.setOutputFiles(TrodanFiles + "," + README);
            
            InetAddress addr = InetAddress.getLocalHost();           
            
            Company company;
            try {
                company = PortalUtil.getCompany(request);
                String gateway = company.getName();
                
                // Send a notification email to the user if enabled.
                if (TRODAN_Parameters[15]!=null)
                    if ( (SMTP_HOST==null) || 
                         (SMTP_HOST.trim().equals("")) ||
                         (SMTP_HOST.trim().equals("N/A")) ||
                         (SENDER_MAIL==null) || 
                         (SENDER_MAIL.trim().equals("")) ||
                         (SENDER_MAIL.trim().equals("N/A"))
                       )
                    log.info ("\nThe Notification Service is not properly configured!!");
                else {
                            // Enabling Job's notification via email
                            TrodanMultiJobSubmission.setUserEmail(emailAddress);
                        
                            sendHTMLEmail(username, 
                                       emailAddress, 
                                       SENDER_MAIL, 
                                       SMTP_HOST, 
                                       "TRODAN", 
                                       gateway);
                }                                
                
            } catch (PortalException ex) {
                Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Submitting...
            InfrastructureInfo infrastructure = 
                TrodanMultiJobSubmission.getInfrastructure();
                
            String Middleware = null;
            if (infrastructure.getMiddleware().equals("glite"))
                Middleware = "glite";
            if (infrastructure.getMiddleware().equals("wsgram"))
                Middleware = "wsgram";
            
            if (trodan_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n- Selected Infrastructure = " 
                    + infrastructure.getName());
                log.info("\n- Enabled Middleware = " 
                    + Middleware);
            }
            
            // Check if more than one infrastructure have been enabled                
            if (MAX==1) 
            {
                String trodan_VONAME = "";
                String trodan_TOPBDII = "";
                String RANDOM_CE = "";                    
                   
                if (cometa_trodan_ENABLEINFRASTRUCTURE != null &&
                    cometa_trodan_ENABLEINFRASTRUCTURE.equals("cometa")) 
                {
                    // Getting the TRODAN VONAME from the portlet preferences for the COMETA VO
                    trodan_VONAME = portletPreferences.getValue("cometa_trodan_VONAME", "N/A");
                    // Getting the TRODAN TOPPBDII from the portlet preferences for the COMETA VO
                    trodan_TOPBDII = portletPreferences.getValue("cometa_trodan_TOPBDII", "N/A");
                    
                    // Set the queue if it is defined by the user
                    if (!TRODAN_Parameters[1].isEmpty()) {
                        log.info("\n- Submitting of the job to the selected CE in progress...");                        
                        //RANDOM_CE = getRandomCE(trodan_VONAME, trodan_TOPBDII, trodan_SOFTWARE, TRODAN_Parameters[1]);
                        TrodanMultiJobSubmission.setJobQueue(TRODAN_Parameters[1]);
                    } else { 
                        // Get the random CE for the TRODAN portlet
                        RANDOM_CE = getRandomCE(trodan_VONAME, trodan_TOPBDII, trodan_SOFTWARE, "");
                        log.info("\n- Submitting of the job to the CE [ " + RANDOM_CE + " ] in progress...");                
                        TrodanMultiJobSubmission.setJobQueue(RANDOM_CE.toString().trim());
                    }
                                            
                }
                    
                if (gridit_trodan_ENABLEINFRASTRUCTURE != null &&
                    gridit_trodan_ENABLEINFRASTRUCTURE.equals("gridit")) 
                {
                    // Getting the TRODAN VONAME from the portlet preferences for the GRIDIT VO
                    trodan_VONAME = portletPreferences.getValue("gridit_trodan_VONAME", "N/A");
                    // Getting the TRODAN TOPPBDII from the portlet preferences for the GRIDIT VO
                    trodan_TOPBDII = portletPreferences.getValue("gridit_trodan_TOPBDII", "N/A");
                        
                    // Set the queue if it is defined by the user
                    if (!TRODAN_Parameters[1].isEmpty()) {
                        log.info("\n- Submitting of the job to the selected CE in progress...");                        
                        //RANDOM_CE = getRandomCE(trodan_VONAME, trodan_TOPBDII, trodan_SOFTWARE, TRODAN_Parameters[1]);
                        TrodanMultiJobSubmission.setJobQueue(TRODAN_Parameters[1]);
                    } else { 
                        // Get the random CE for the TRODAN portlet
                        RANDOM_CE = getRandomCE(trodan_VONAME, trodan_TOPBDII, trodan_SOFTWARE, "");
                        log.info("\n- Submitting of the job to the CE [ " + RANDOM_CE + " ] in progress...");                
                        TrodanMultiJobSubmission.setJobQueue(RANDOM_CE.toString().trim());
                    }
                }
                    
                if (eumed_trodan_ENABLEINFRASTRUCTURE != null &&
                    eumed_trodan_ENABLEINFRASTRUCTURE.equals("eumed")) 
                {
                    // Getting the TRODAN VONAME from the portlet preferences for the EUMED VO
                    trodan_VONAME = portletPreferences.getValue("eumed_trodan_VONAME", "N/A");
                    // Getting the TRODAN TOPPBDII from the portlet preferences for the EUMED VO
                    trodan_TOPBDII = portletPreferences.getValue("eumed_trodan_TOPBDII", "N/A");
                        
                    // Set the queue if it is defined by the user
                    if (!TRODAN_Parameters[1].isEmpty()) {
                        log.info("\n- Submitting of the job to the selected CE in progress...");
                        //RANDOM_CE = getRandomCE(trodan_VONAME, trodan_TOPBDII, trodan_SOFTWARE, TRODAN_Parameters[1]);
                        log.info("\n- Selected Computing Element = " + TRODAN_Parameters[1]);
                        TrodanMultiJobSubmission.setJobQueue(TRODAN_Parameters[1]);
                    } else { 
                        // Get the random CE for the TRODAN portlet
                        RANDOM_CE = getRandomCE(trodan_VONAME, trodan_TOPBDII, trodan_SOFTWARE, "");
                        log.info("\n- Submitting of the job to the CE [ " + RANDOM_CE + " ] in progress...");                
                        TrodanMultiJobSubmission.setJobQueue(RANDOM_CE.toString().trim());
                    }
                }
                    
                if (biomed_trodan_ENABLEINFRASTRUCTURE != null &&
                    biomed_trodan_ENABLEINFRASTRUCTURE.equals("biomed")) 
                {
                    // Getting the TRODAN VONAME from the portlet preferences for the BIOMED VO
                    trodan_VONAME = portletPreferences.getValue("biomed_trodan_VONAME", "N/A");
                    // Getting the TRODAN TOPPBDII from the portlet preferences for the BIOMED VO
                    trodan_TOPBDII = portletPreferences.getValue("biomed_trodan_TOPBDII", "N/A");
                        
                    // Set the queue if it is defined by the user
                    if (!TRODAN_Parameters[1].isEmpty()) {
                        log.info("\n- Submitting of the job to the selected CE in progress...");
                        //RANDOM_CE = getRandomCE(trodan_VONAME, trodan_TOPBDII, trodan_SOFTWARE, TRODAN_Parameters[1]);
                        TrodanMultiJobSubmission.setJobQueue(TRODAN_Parameters[1]);
                    } else { 
                        // Get the random CE for the TRODAN portlet
                        RANDOM_CE = getRandomCE(trodan_VONAME, trodan_TOPBDII, trodan_SOFTWARE, "");
                        log.info("\n- Submitting of the job to the CE [ " + RANDOM_CE + " ] in progress...");                
                        TrodanMultiJobSubmission.setJobQueue(RANDOM_CE.toString().trim());
                    }
                }                    
            }
                
            TrodanMultiJobSubmission.submitJobAsync(
                        infrastructure,
                        username,
                        addr.getHostAddress()+":8162",
                        Integer.valueOf(trodan_APPID),
                        TRODAN_Parameters[0]);
            
        } // end PROCESS ACTION [ SUBMIT_TRODAN_PORTLET ]
    }

    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response)
                throws PortletException, IOException
    {
        //super.serveResource(request, response);

        PortletPreferences portletPreferences = (PortletPreferences) request.getPreferences();

        final String action = (String) request.getParameter("action");

        if (action.equals("get-ratings")) {
            //Get CE Ratings from the portlet preferences
            String trodan_CE = (String) request.getParameter("trodan_CE");

            String json = "{ \"avg\":\"" + 
                    portletPreferences.getValue(trodan_CE+"_avg", "0.0") +
                    "\", \"cnt\":\"" + portletPreferences.getValue(trodan_CE+"_cnt", "0") + "\"}";

            response.setContentType("application/json");
            response.getPortletOutputStream().write( json.getBytes() );

        } else if (action.equals("set-ratings")) {

            String trodan_CE = (String) request.getParameter("trodan_CE");
            int vote = Integer.parseInt(request.getParameter("vote"));

             double avg = Double.parseDouble(portletPreferences.getValue(trodan_CE+"_avg", "0.0"));
             long cnt = Long.parseLong(portletPreferences.getValue(trodan_CE+"_cnt", "0"));

             portletPreferences.setValue(trodan_CE+"_avg", Double.toString(((avg*cnt)+vote) / (cnt +1)));
             portletPreferences.setValue(trodan_CE+"_cnt", Long.toString(cnt+1));

             portletPreferences.store();
        }
    }
    
    private void storeFileFromList (String fileName, List<String> list) 
                              throws IOException 
    { 
        log.info("\n- Writing ASCII file [ " + fileName + " ]");
        
        BufferedWriter writer = 
                new BufferedWriter(new FileWriter(fileName));
        
        for (int i=0; i<list.size(); i++)
            writer.write(list.get(i)+"\n");
            
        writer.close();
    }
    
    private void storeFile (String fileName, String[] fileContent, String value) 
                              throws IOException 
    { 
        log.info("\n- Writing ASCII file [ " + fileName + " ]");
        
        BufferedWriter writer = 
                new BufferedWriter(new FileWriter(fileName));
        
        if (value.equals("Arguments"))
        for (int i = 2; i < 12; i++)
        {
            if (fileContent[i]!=null) {                
                if (i==2) writer.write("6");
                if (i==3) writer.write("7");
                if (i==4) writer.write("4");
                if (i==5) writer.write("8");
                if (i==6) writer.write("5");
                if (i==7) writer.write("9");
                if (i==8) writer.write("11");
                if (i==9) writer.write("10");
                if (i==10) writer.write("12");
                if (i==11) writer.write("13");                
                writer.write("\n");
            }
        }
        
        if (value.equals("Stations"))
        for (int i = 0; i < fileContent.length; i++)
            writer.write(fileContent[i]+"\n");
        
        writer.close();                
    }

    // Upload TRODAN input files
    public String[] uploadTrodanSettings(ActionRequest actionRequest,
                                        ActionResponse actionResponse,
                                        String[] list,
                                        String username)
    {
        //String[] TRODAN_Parameters = new String [20];
        boolean status;

        // Create a factory for disk-based file items.
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Set factory constrains
        File TRODAN_Repository = new File ("/tmp");
        if (!TRODAN_Repository.exists()) status = TRODAN_Repository.mkdirs();
        factory.setRepository(TRODAN_Repository);

        // Create a new file upload handler.
        PortletFileUpload upload = new PortletFileUpload(factory);

        try {
                    // Parse the request
                    List items = upload.parseRequest(actionRequest);

                    // Processing items
                    Iterator iter = items.iterator();

                    while (iter.hasNext())
                    {
                        FileItem item = (FileItem) iter.next();

                        /*String fieldName = item.getFieldName();
                        
                        DateFormat dateFormat = 
                                new SimpleDateFormat("yyyyMMddHHmmss");
                        
                        String timeStamp = 
                                dateFormat.format(Calendar.getInstance().getTime());*/

                        // Processing a regular form field
                        if ( item.isFormField() )
                        {                                                        
                            
                        }                                                 
                    } // end while
            } catch (FileUploadException ex) {
              Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
              Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
            }
        //}

        return null;
    }
    
    // Retrieve a random Computing Element
    // matching the Software Tag for the application    
    public String getRandomCE(String trodan_VONAME,
                              String trodan_TOPBDII,
                              String trodan_SOFTWARE,                              
                              String selected)
                              throws PortletException, IOException
    {
        String randomCE = null;
        BDII bdii = null;    
        List<String> CEqueues = null;
        boolean flag = true;        
                        
        log.info("\n- Querying the Information System [ " + 
                  trodan_TOPBDII + 
                  " ] and retrieving a random CE matching the SW tag [ VO-" + 
                  trodan_VONAME +
                  "-" +
                  trodan_SOFTWARE + " ]");  

       try {               

                bdii = new BDII( new URI(trodan_TOPBDII) );               
                
                // Get the list of the available queues
                CEqueues = bdii.queryCEQueues(trodan_VONAME);
                                
                // Get the list of the Computing Elements for the given SW tag
                randomCE = bdii.getRandomCEForSWTag("VO-" + 
                           trodan_VONAME + 
                           "-" +
                           trodan_SOFTWARE);
                
                /*randomCE = bdii.getRandomCEFromSWTag_MaxCPUTime(
                        "VO-" + trodan_VONAME + "-" + trodan_SOFTWARE, 
                        trodan_VONAME, 
                        trodan_MaxCPUTime);*/
                                    
                /*if (!selected.isEmpty())
                while (flag) {
                    // Fetching the Queues
                    for (String CEqueue:CEqueues) {
                        // Selecting only the "infinite" queue
                            if (CEqueue.contains(selected)) {
                                randomCE=CEqueue;                    
                                flag=false;
                            }                        
                    }
                }*/
                
                // Fetching the Queues
                for (String CEqueue:CEqueues) {
                    if (CEqueue.contains(randomCE))
                        randomCE=CEqueue;
                }

        } catch (URISyntaxException ex) {
                Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
                Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
        }                   
        
        if (!selected.isEmpty()) log.info("\n- Selected the following computing farm =  " + randomCE);
        else log.info("\n- Selected *randomly* the following computing farm =  " + randomCE);
        return randomCE;
    }        
    
    public String RemoveCarriageReturn (String InputFileName, String OutputFileName)             
    {
        // Remove the carriage return char from a named file.                                
        FileInputStream fis;
        try {
            
            fis = new FileInputStream(InputFileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            
            File fout = new File(OutputFileName);
            FileOutputStream fos = new FileOutputStream(fout);                                
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            
            // The pattern matches control characters
            Pattern p = Pattern.compile("\r");
            Matcher m = p.matcher("");
            String aLine = null;

            try {
                while((aLine = in.readLine()) != null) {
                    m.reset(aLine);
                    //Replaces control characters with an empty string.
                    String result = m.replaceAll("");                    
                    out.write(result);
                    out.newLine();
                }
                out.close();                
            } catch (IOException ex) {
                Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
        }                                                                                
        
        log.info("\n- Writing the user's stripped file: [ " + 
                  OutputFileName.toString() + " ] to disk");
        
        return OutputFileName;
    }


    // Retrieve the list of Computing Elements
    // matching the Software Tag for the TRODAN application    
    public List<String> getListofCEForSoftwareTag(String trodan_VONAME,
                                                  String trodan_TOPBDII,
                                                  String trodan_SOFTWARE)
                                throws PortletException, IOException
    {
        List<String> CEs_list = null;
        BDII bdii = null;        
        
        log.info("\n- Querying the Information System [ " + 
                  trodan_TOPBDII + 
                  " ] and looking for CEs matching SW tag [ VO-" + 
                  trodan_VONAME +
                  "-" +
                  trodan_SOFTWARE + " ]");  

       try {               

                bdii = new BDII( new URI(trodan_TOPBDII) );                
                CEs_list = bdii.queryCEForSWTag(
                           "VO-" +
                           trodan_VONAME +
                           "-" +
                           trodan_SOFTWARE);

        } catch (URISyntaxException ex) {
                Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
                Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
        }

        return CEs_list;
    }

    // Get the GPS location of the given grid resource
    public String[] getCECoordinate(RenderRequest request,
                                    String CE)
                                    throws PortletException, IOException
    {
        String[] GPS_locations = null;
        BDII bdii = null;

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        // Getting the TRODAN TOPPBDII from the portlet preferences
        String cometa_trodan_TOPBDII = 
                portletPreferences.getValue("cometa_trodan_TOPBDII", "N/A");
        String gridit_trodan_TOPBDII = 
                portletPreferences.getValue("gridit_trodan_TOPBDII", "N/A");
        String eumed_trodan_TOPBDII = 
                portletPreferences.getValue("eumed_trodan_TOPBDII", "N/A");
        String biomed_trodan_TOPBDII = 
                portletPreferences.getValue("biomed_trodan_TOPBDII", "N/A");
        
        // Getting the TRODAN ENABLEINFRASTRUCTURE from the portlet preferences
        String trodan_ENABLEINFRASTRUCTURE = 
                portletPreferences.getValue("trodan_ENABLEINFRASTRUCTURE", "N/A");

            try {
                if ( trodan_ENABLEINFRASTRUCTURE.equals("cometa") )
                     bdii = new BDII( new URI(cometa_trodan_TOPBDII) );
                
                if ( trodan_ENABLEINFRASTRUCTURE.equals("gridit") )
                     bdii = new BDII( new URI(gridit_trodan_TOPBDII) );

                if ( trodan_ENABLEINFRASTRUCTURE.equals("eumed") )
                     bdii = new BDII( new URI(eumed_trodan_TOPBDII) );

                if ( trodan_ENABLEINFRASTRUCTURE.equals("biomed") )
                    bdii = new BDII( new URI(biomed_trodan_TOPBDII) );

                GPS_locations = bdii.queryCECoordinate("ldap://" + CE + ":2170");

            } catch (URISyntaxException ex) {
                Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Trodan.class.getName()).log(Level.SEVERE, null, ex);
            }

            return GPS_locations;
    }
    
    /*private void storeString (String fileName, String fileContent) 
                              throws IOException 
    { 
        log.info("\n- Writing textarea in a ASCII file [ " + fileName + " ]");        
        // Removing the Carriage Return (^M) from text
        String pattern = "[\r]";
        String stripped = fileContent.replaceAll(pattern, "");        
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));        
        writer.write(stripped);
        writer.write("\n");
        writer.close();
    }*/
    
    private void sendHTMLEmail (String USERNAME,
                                String TO, 
                                String FROM, 
                                String SMTP_HOST, 
                                String ApplicationAcronym,
                                String GATEWAY)
    {
                
        log.info("\n- Sending email notification to the user " + USERNAME + " [ " + TO + " ]");
        
        log.info("\n- SMTP Server = " + SMTP_HOST);
        log.info("\n- Sender = " + FROM);
        log.info("\n- Receiver = " + TO);
        log.info("\n- Application = " + ApplicationAcronym);
        log.info("\n- Gateway = " + GATEWAY);        
        
        // Assuming you are sending email from localhost
        String HOST = "localhost";
        
        // Get system properties
        Properties properties = System.getProperties();
        properties.setProperty(SMTP_HOST, HOST);
        
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        
        try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(FROM));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
         //message.addRecipient(Message.RecipientType.CC, new InternetAddress(FROM));

         // Set Subject: header field
         message.setSubject(" [liferay-sg-gateway] - [ " + GATEWAY + " ] ");

	 Date currentDate = new Date();
	 currentDate.setTime (currentDate.getTime());

         // Send the actual HTML message, as big as you like
         message.setContent(
	 "<br/><H4>" +         
         "<img src=\"http://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc6/195775_220075701389624_155250493_n.jpg\" width=\"100\">Science Gateway Notification" +
	 "</H4><hr><br/>" +
         "<b>Description:</b> Notification for the application <b>[ " + ApplicationAcronym + " ]</b><br/><br/>" +         
         "<i>The application has been successfully submitted from the [ " + GATEWAY + " ]</i><br/><br/>" +
         "<b>TimeStamp:</b> " + currentDate + "<br/><br/>" +
	 "<b>Disclaimer:</b><br/>" +
	 "<i>This is an automatic message sent by the Science Gateway based on Liferay technology.<br/>" + 
	 "If you did not submit any jobs through the Science Gateway, please " +
         "<a href=\"mailto:" + FROM + "\">contact us</a></i>",
	 "text/html");

         // Send message
         Transport.send(message);         
      } catch (MessagingException mex) { mex.printStackTrace(); }        
    }
    
    public String getStation (String station)
    {
        String[] parts = station.split("\\_");
        return (parts[0].substring(1,parts[0].length()));
    }
    
    public List<String> getStations(String json)
    {
        List<String> list = new ArrayList<String>();

        log.debug(json);

        //Gson gson = new Gson();
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        JsonArray results = obj.get("results").getAsJsonArray();

        for (int i=0; i<results.size(); i++)
        {
                String FileName =
                results.get(i).getAsJsonObject().get("FileName").toString();

                String Station = getStation(FileName);
                if (!FileName.equals("")) list.add(Station);
        }

        return list;
    }
    
    public List<String> getID(String json, String Station)
    {
        List<String> list = new ArrayList<String>();                
        
        //Gson gson = new Gson();        
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        JsonArray results = obj.get("results").getAsJsonArray();

        for (int i=0; i<results.size(); i++)
        {
                String FileName =
                results.get(i).getAsJsonObject().get("FileName").toString();

                String StationID =
                results.get(i).getAsJsonObject().get("id").toString();

                if (FileName.contains(Station)) list.add(StationID);
        }

        return list;
    }
    
    public static List<String> getTotalStations(String json)
    {
        List<String> list = new ArrayList<String>();

        //Gson gson = new Gson();
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        JsonArray results = obj.get("results").getAsJsonArray();

        list.add(Integer.toString(results.size()));

        return (list);
    }

    public List<String> getReplicas(String json)
    {
        List<String> list = new ArrayList<String>();

        //Gson gson = new Gson();
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        JsonObject results = obj.get("results").getAsJsonObject();
        JsonArray urls = results.get("Replicas").getAsJsonArray();

        for (int i=0; i<urls.size(); i++)
        {
                String url =
                urls.get(i).getAsJsonObject().get("url").toString();

                if (!url.equals("")) list.add(url);
        }

        return list;
    }
    
    private List<String> doHTTPRequest (Properties properties, String LOG_LEVEL) 
            throws Exception
    {            
	log.info("");log.info(properties);       
        
    	HttpClient httpclient = new DefaultHttpClient();
	HttpGet httpget = null;
        
        List<String> list = new ArrayList<String>();
        
        String request = "";

	try {
            // A C T I O N ~ T O ~ B E ~ P R O C E S S E D => 
            // *** [RETRIEVING REPOSITORIES and/or STATION_ID and/or #STATIONS from SERVER] ***
            if ( properties.getProperty("ACTION").equals("list") ||
                 properties.getProperty("ACTION").equals("getID") ||
                 properties.getProperty("ACTION").equals("getTotal"))
            {
                request = "http://" 
                    + properties.getProperty("SERVER_HOST") + ":" 
                    + properties.getProperty("SERVER_PORT") + "/" 
                    + properties.getProperty("REPOSITORY") + "/" 
                    + properties.getProperty("TYPE") + "/entries";
            } 

            // A C T I O N ~ T O ~ B E ~ P R O C E S S E D => 
            // *** [RETRIEVING REPLICAS details from SERVER] ***
            if ( properties.getProperty("ACTION").equals("getURL") )
            {
                request = "http://"
                    + properties.getProperty("SERVER_HOST") + ":"
                    + properties.getProperty("SERVER_PORT") + "/"
                    + properties.getProperty("REPOSITORY") + "/"
                    + properties.getProperty("TYPE") + "/"
                    + properties.getProperty("ID");
            }
            
            log.info("REQUEST = " +request);
	    httpget = new HttpGet(request);

	    // Create a response handler
	    ResponseHandler<String> responseHandler = 
                    new BasicResponseHandler();
	        
            String responseBody = 
                    httpclient.execute(httpget, responseHandler);

	    if (responseBody != "") 
            {                
		if ( properties.getProperty("ACTION").equals("getTotal") )
                    list = getTotalStations(responseBody);

                if ( properties.getProperty("ACTION").equals("list") )
                    list = getStations(responseBody);

                if ( properties.getProperty("ACTION").equals("getURL") )
                    list = getReplicas(responseBody);

                if ( properties.getProperty("ACTION").equals("getID") )
                    list = getID(responseBody, properties.getProperty("STATION"));
                
            } else log.info("\n" + responseBody);

    } finally {
       	// When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
       	// immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();
    }
        
    return list;
    }    
}