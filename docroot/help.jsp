<%
/**************************************************************************
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
****************************************************************************/
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.portlet.*"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<portlet:defineObjects/>

<script type="text/javascript">
    $(document).ready(function() {
              
    $('.slideshow').cycle({
	fx: 'fade' // choose your transition type (fade, scrollUp, shuffle, etc)
    });
    
    // Roller
    $('#trodan_footer').rollchildren({
        delay_time         : 3000,
        loop               : true,
        pause_on_mouseover : true,
        roll_up_old_item   : true,
        speed              : 'slow'   
    });
    
    //var $tumblelog = $('#tumblelog');  
    $('#tumblelog').imagesLoaded( function() {
      $tumblelog.masonry({
        columnWidth: 440
      });
    });
});
</script>
                    
<br/>

<fieldset>
<legend>About the project</legend>

<section id="content">

<div id="tumblelog" class="clearfix">
    
  <div class="story col3">
  <table border="0">
  <tr>
  <td>                
    
    <img 
        align="center" width="120" height="120" 
        src="<%= renderRequest.getContextPath()%>/images/NASRDA_logo.png"
        border="0"/>
    </a>
  </td>
  <td> &nbsp;&nbsp; </td>
  
  <td>
  <p style="text-align:justify; position: relative;">
  ~ The <a href="http://www.carnasrda.com/">Centre for Atmospheric Research</a> (CAR) is an activity Centre of the 
  <a href="http://nasrda.gov.ng/en/portal/">Nigerian National Space Research and Development Agency</a>, NASRDA, 
  committed to research and capacity building in the atmospheric and related sciences. 
      
  CAR is dedicated to understanding the atmosphere the air around us and the 
  interconnected processes that make up the Earth system, from the ocean floor 
  through the ionosphere to the Sun's core. <br/>
  The NASRDA Centre for Atmospheric Research provides research facilities and 
  services for the atmospheric and Earth Sciences community. <br/>
  Tropospheric Data Acquisition Network, TRODAN, is a project that was designed 
  to monitor the lower atmosphere which covers region from the surface of the 
  Earth to the altitude of about 11 km.  
  <br/><br/>
  </p>
  </td>
  </tr>
  
  <tr>
  <td colspan="3">
  <p style="text-align:justify; position: relative;">
  This project is designed to collect and provide real-time meteorological data 
  from different locations across Nigeria using for the purpose of research and 
  development. <br/>
  At moment TRODAN equipment include atmospheric monitoring facilities such as 
  Automatic Weather Stations, Micro Rain Radar facilities and Vantage Pro. 
  This present data is obtained using Campbell Scientific Automatic Weather Station.
  <br/><br/>
  
  <b>Conditions of Use of TRODAN Data</b><br/> 
  The data made available by CAR are provided for research use and are not for 
  commercial use or sale or distribution to third parties without the written 
  permission of the Centre. 
  Publications including theses making use of the data should include an acknowledgment 
  statement of the form given below. <br/>
  A citation reference should be sent to the <a href="mailto:trodan@carnasrda.com">
  TRODAN Project Manager</a> for inclusion in a publications list on the TRODAN website. <br/><br/>
  
  <b>The TRODAN Data Structure</b><br/>
  In all files, the header text lines contain thirteen rows which begin with 
  date/time in the same cell with the format <i>ddmmyyyyhhmm</i>.<br/>
  The second raw is CR 1000 record which is the datalogger type used for data 
  collection in this equipment, the third row is the CR 1000 Battery Volt, the 
  fourth row begins the meteorological parameters starting with:<br/><br/>
  - <i>Rain Rate</i> in mm, <br/>
  - <i>Solar Radiation</i> (SLrW) in W/m&#178;, <br/>
  - <i>Air Temperature</i> (AirTC) in Degree Celsius (°C), <br/>
  - <i>Relative Humidity</i> (RH) in Percentage (%), <br/>
  - <i>Soil Temperature</i> (T107) in °C, <br/>
  - <i>Wind Speed</i> (WS) in m/s, <br/>
  - <i>Wind Direction</i> (WD) in Degrees, <br/>
  - <i>Barometric Pressure</i> (Barpress) in mbar, <br/>
  - <i>Volumetric Water Content</i> (VW) *100<br/>
  - <i>PA_us</i> conversion for unified soil which concern only Volumetric water content.<br/><br/>
  The column contains the serial number of the data. <br/>
  The header contains metadata on product name, creation time, units, source, missing data, 
  end of record identifier, time range. <br/>
  The product filename includes the begin date/time (Local time) of each product 
  run in the format of ddmmyyyyhhmm 
  Where dd = 2-digit day, MM = 2-digit month hh = 2-digit hour, yyyy = 4-digit year, 
  hh= 2-digit hour and mm = 2-digit minute. <br/><br/>
  The product run interval is 5 minutes and is indicated by the Time Range field in the header. 
  <br/> NAN=Missing Data Note that: There is unavailable data from 24th/05/2010 20:25 
  to 30th/09/2010 20:30.<br/><br/>
  
  <b>Disclaimer</b><br/>
  CAR-NASRDA accepts no liability for the use or transmission of this data.
  <br/><br/>
  </p>  
  </td>  
  </tr>
  </table>  
  </div>

  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">      
      <h2><a href="mailto:trodan@carnasrda.com">
      <img width="100" src="<%= renderRequest.getContextPath()%>/images/contact6.jpg" border="0" title="Get in touch with the project"/></a>Contacts</h2>
      <h3><u>Main Authors</u></h3>
      <p>      
      <a href="mailto:trodan@carnasrda.com">TRODAN Project Manager</a>
      </p>            
  </div>               
    
  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
        <h2>Sponsors</h2>
        <table border="0">                                                
            <tr>
            <td>
            <p align="center">
            <a href="http://www.infn.it/">
            <img align="center" width="150" heigth="150"
                 src="<%= renderRequest.getContextPath()%>/images/Infn_Logo.png" 
                 border="0" title="Istituto Nazionale di Fisica Nucleare"/>
            </a>
            </p>
            </td>
            
            <td>
            <p align="center">
            <a href="http://ei4africa.eu/">
            <img align="center" width="220" heigth="170"
                 src="<%= renderRequest.getContextPath()%>/images/eI4Africa.png" 
                 border="0" title="eI4Africa - The eI4Africa Project" />
            </a>
            </p>
            </td>
            </tr>
        </table>   
  </div>
</div>
</section>
</fieldset>
           
<div id="trodan_footer" 
     style="width:690px; 
     font-family: Tahoma,Verdana,sans-serif,Arial; 
     font-size: 14px;">
    
    <div>TRODAN Data Repository Visualization ~ ver. 1.0.3</div>
    <div>Istituto Nazionale di Fisica Nucleare (INFN), Copyright © 2014</div>
    <div>All rights reserved</div>
    <div>This work has been partially supported by
        <a href="http://ei4africa.eu/">
            <img width="90" 
                 border="0"
                 src="<%= renderRequest.getContextPath()%>/images/eI4Africa.png" 
                 title="eI4Africa - The eI4Africa Project" />
        </a>
    </div>
</div>