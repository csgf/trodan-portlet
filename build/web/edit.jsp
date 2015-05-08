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
<%@page import="javax.portlet.*"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<%
  //
  // TRODAN 1.0.3 portlet preferences for the GridEngine interaction
  //
  // These parameters are:  
  // o *_trodan_INFRASTRUCTURE  - The Infrastructure Acronym to be enabled
  // o *_trodan_TOPBDII         - The TopBDII hostname for accessing the Infrastructure
  // o *_trodan_WMS             - The WMProxy hostname for accessing the Infrastructure
  // o *_trodan_VONAME          - The VO name
  // o *_trodan_ETOKENSERVER    - The eTokenServer hostname to be contacted for 
  //                              requesting grid proxies
  // o *_trodan_MYPROXYSERVER   - The MyProxyServer hostname for requesting 
  //                              long-term grid proxies
  // o *_trodan_PORT            - The port on which the eTokenServer is listening
  // o *_trodan_METADATASERVER  - The MetaDataServer hostname
  // o *_trodan_METADATAPORT    - The port on which the MetadataServer is listening
  // o *_trodan_ROBOTID         - The robotID to generate the grid proxy
  // o *_trodan_ROLE            - The FQAN for the grid proxy (if any)
  // o *_trodan_REWAL           - Enable the creation of a long-term proxy to a 
  //                              MyProxy Server (default 'yes')
  // o *_trodan_DISABLEVOMS     - Disable the creation of a VOMS proxy (default 'no')
  //
  // o trodan_APPID             - The ApplicationID for the TRODAN application
  // o trodan_LOGLEVEL          - The portlet log level (INFO, VERBOSE)
  // o trodan_OUTPUT_PATH       - The ApplicationID for the TRODAN results
  // o trodan_REPOSITORTY       - The MetaData Repository to query
  // o trodan_SOFTWARE          - The Application Software requested by the application
  // o TRACKING_DB_HOSTNAME     - The Tracking DB server hostname 
  // o TRACKING_DB_USERNAME     - The username credential for login
  // o TRACKING_DB_PASSWORD     - The password for login
%>

<jsp:useBean id="cometa_trodan_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="cometa_trodan_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_METADATASERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_METADATAPORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gridit_trodan_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="gridit_trodan_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_METADATASERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_METADATAPORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="eumed_trodan_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="eumed_trodan_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_METADATASERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_METADATAPORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="biomed_trodan_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="biomed_trodan_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_METADATASERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_METADATAPORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="trodan_ENABLEINFRASTRUCTURE" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="trodan_APPID" class="java.lang.String" scope="request"/>
<jsp:useBean id="trodan_LOGLEVEL" class="java.lang.String" scope="request"/>
<jsp:useBean id="trodan_OUTPUT_PATH" class="java.lang.String" scope="request"/>
<jsp:useBean id="trodan_REPOSITORY" class="java.lang.String" scope="request"/>
<jsp:useBean id="trodan_SOFTWARE" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_HOSTNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_USERNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_PASSWORD" class="java.lang.String" scope="request"/>

<jsp:useBean id="SMTP_HOST" class="java.lang.String" scope="request"/>
<jsp:useBean id="SENDER_MAIL" class="java.lang.String" scope="request"/>

<script type="text/javascript">
    
    //var EnabledInfrastructure = "<%= trodan_ENABLEINFRASTRUCTURE %>";    
    //console.log(EnabledInfrastructure);        
            
    $(document).ready(function() { 
        
        var EnabledInfrastructure = null;           
        var infrastructures = new Array();  
        var i = 0;    
    
        <c:forEach items="<%= trodan_ENABLEINFRASTRUCTURE %>" var="current">
        <c:choose>
        <c:when test="${current!=null}">
            infrastructures[i] = '<c:out value='${current}'/>';       
            i++;  
        </c:when>
        </c:choose>
        </c:forEach>
        
        for (var i = 0; i < infrastructures.length; i++) {
        console.log("Reading array = " + infrastructures[i]);
        if (infrastructures[i]=="cometa") EnabledInfrastructure='cometa';        
        if (infrastructures[i]=="gridit") EnabledInfrastructure='gridit';        
        if (infrastructures[i]=="eumed")  EnabledInfrastructure='eumed';
        if (infrastructures[i]=="biomed") EnabledInfrastructure='biomed';
        }
        
        var NMAX = infrastructures.length;
        console.log (NMAX);
        console.log (EnabledInfrastructure);
                
        var cometa_inputs = 1;        
        // ADDING a new WMS enpoint for the COMETA infrastructure (MAX. 5)
        $('#adding_WMS_cometa').click(function() {        
            ++cometa_inputs;        
            if (cometa_inputs>1 && cometa_inputs<6) {
            var c = $('.cloned_cometa_trodan_WMS:first').clone(true);            
            c.children(':text').attr('name','cometa_trodan_WMS' );
            c.children(':text').attr('id','cometa_trodan_WMS' );
            $('.cloned_cometa_trodan_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the COMETA infrastructure
        $('.btnDel_cometa').click(function() {
        if (cometa_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --cometa_inputs;
            $(this).closest('.cloned_cometa_trodan_WMS').remove();
            $('.btnDel_cometa').attr('disabled',($('.cloned_cometa_trodan_WMS').length  < 2));
        }
        });
        
        $('.btnDel_cometa2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --cometa_inputs;
            $(this).closest('.cloned_cached_cometaWMS').remove();
            $('.btnDel_cometa2').attr('disabled',($('.cloned_cached_cometaWMS').length  < 2));
        }
        });
                
        var gridit_inputs = 1;        
        // ADDING a new WMS enpoint for the GRIDIT infrastructure (MAX. 5)
        $('#adding_WMS_gridit').click(function() {        
            ++gridit_inputs;        
            if (gridit_inputs>1 && gridit_inputs<6) {
            var c = $('.cloned_gridit_trodan_WMS:first').clone(true);            
            c.children(':text').attr('name','gridit_trodan_WMS' );
            c.children(':text').attr('id','gridit_trodan_WMS' );
            $('.cloned_gridit_trodan_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the GRIDIT infrastructure
        $('.btnDel_gridit').click(function() {
        if (gridit_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --gridit_inputs;
            $(this).closest('.cloned_gridit_trodan_WMS').remove();
            $('.btnDel_gridit').attr('disabled',($('.cloned_gridit_trodan_WMS').length  < 2));
        }
        });
        
        $('.btnDel_gridit2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --gridit_inputs;
            $(this).closest('.cloned_cached_griditWMS').remove();
            $('.btnDel_gridit2').attr('disabled',($('.cloned_cached_griditWMS').length  < 2));
        }
        });
                        
        
        var eumed_inputs = 1;        
        // ADDING a new WMS enpoint for the EUMED infrastructure (MAX. 5)
        $('#adding_WMS_eumed').click(function() {        
            ++eumed_inputs;        
            if (eumed_inputs>1 && eumed_inputs<6) {
            var c = $('.cloned_eumed_trodan_WMS:first').clone(true);            
            c.children(':text').attr('name','eumed_trodan_WMS' );
            c.children(':text').attr('id','eumed_trodan_WMS' );
            $('.cloned_eumed_trodan_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the EUMED infrastructure
        $('.btnDel_eumed').click(function() {
        if (eumed_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --eumed_inputs;
            $(this).closest('.cloned_eumed_trodan_WMS').remove();
            $('.btnDel_eumed').attr('disabled',($('.cloned_eumed_trodan_WMS').length  < 2));
        }
        });
                
        $('.btnDel_eumed2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --eumed_inputs;
            $(this).closest('.cloned_cached_eumedWMS').remove();
            $('.btnDel_eumed2').attr('disabled',($('.cloned_cached_eumedWMS').length  < 2));
        }
        });
        
        var biomed_inputs = 1;        
        // ADDING a new WMS enpoint for the BIOMED infrastructure (MAX. 5)
        $('#adding_WMS_biomed').click(function() {        
            ++biomed_inputs;        
            if (biomed_inputs>1 && biomed_inputs<6) {
            var c = $('.cloned_biomed_trodan_WMS:first').clone(true);            
            c.children(':text').attr('name','biomed_trodan_WMS' );
            c.children(':text').attr('id','biomed_trodan_WMS' );
            $('.cloned_biomed_trodan_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the BIOMED infrastructure
        $('.btnDel_biomed').click(function() {
        if (biomed_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --biomed_inputs;
            $(this).closest('.cloned_biomed_trodan_WMS').remove();
            $('.btnDel_biomed').attr('disabled',($('.cloned_biomed_trodan_WMS').length  < 2));
        }
        });
        
        $('.btnDel_biomed2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --biomed_inputs;
            $(this).closest('.cloned_cached_biomedWMS').remove();
            $('.btnDel_biomed2').attr('disabled',($('.cloned_cached_biomedWMS').length  < 2));
        }
        });

        // Validate input form
        $('#TrodanEditForm').validate({
            rules: {
                cometa_trodan_INFRASTRUCTURE: {
                    required: true              
                },
                cometa_trodan_VONAME: {
                    required: true              
                },
                cometa_trodan_TOPBDII: {
                    required: true
                },
                cometa_trodan_WMS: {
                    required: true
                },
                cometa_trodan_MYPROXYSERVER: {
                    required: true
                },
                cometa_trodan_ETOKENSERVER: {
                    required: true
                },                
                cometa_trodan_PORT: {
                    required: true
                },
                cometa_trodan_ROBOTID: {
                    required: true
                },
                
                
                gridit_trodan_INFRASTRUCTURE: {
                    required: true              
                },
                gridit_trodan_VONAME: {
                    required: true              
                },
                gridit_trodan_TOPBDII: {
                    required: true
                },
                gridit_trodan_WMS: {
                    required: true
                },
                gridit_trodan_MYPROXYSERVER: {
                    required: true
                },
                gridit_trodan_ETOKENSERVER: {
                    required: true
                },                
                gridit_trodan_PORT: {
                    required: true
                },
                gridit_trodan_ROBOTID: {
                    required: true
                },
                
                                
                eumed_trodan_INFRASTRUCTURE: {
                    required: true              
                },
                eumed_trodan_VONAME: {
                    required: true              
                },
                eumed_trodan_TOPBDII: {
                    required: true
                },
                eumed_trodan_WMS: {
                    required: true
                },
                eumed_trodan_MYPROXYSERVER: {
                    required: true
                },
                eumed_trodan_ETOKENSERVER: {
                    required: true
                },                
                eumed_trodan_PORT: {
                    required: true
                },
                eumed_trodan_ROBOTID: {
                    required: true
                },
                
                
                biomed_trodan_INFRASTRUCTURE: {
                    required: true              
                },
                biomed_trodan_VONAME: {
                    required: true              
                },
                biomed_trodan_TOPBDII: {
                    required: true
                },
                biomed_trodan_WMS: {
                    required: true
                },
                biomed_trodan_MYPROXYSERVER: {
                    required: true
                },
                biomed_trodan_ETOKENSERVER: {
                    required: true
                },                
                biomed_trodan_PORT: {
                    required: true
                },
                biomed_trodan_ROBOTID: {
                    required: true
                },                
                
                trodan_APPID: {
                    required: true              
                },                
                trodan_LOGLEVEL: {
                    required: true              
                },
                trodan_OUTPUT_PATH: {
                    required: true              
                }
            },
            
            invalidHandler: function(form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors) {
                    $("#error_message").empty();
                    var message = errors == 1
                    ? ' You missed 1 field. It has been highlighted'
                    : ' You missed ' + errors + ' fields. They have been highlighted';                    
                    $('#error_message').append("<img width='30' src='<%=renderRequest.getContextPath()%>/images/Warning.png' border='0'>"+message);
                    $("#error_message").show();
                } else $("#error_message").hide();                
            },
            
            submitHandler: function(form) {
                   form.submit();
            }
        });
        
        $("#TrodanEditForm").bind('submit', function () {            
            // Check if the TRODAN OPTIONS are NULL
            if ( !$('#cometa_trodan_RENEWAL').is(':checked') && 
                 !$('#cometa_trodan_DISABLEVOMS').is(':checked') 
             ) $('#cometa_trodan_OPTIONS').val('NULL');
                 
            if ( !$('#gridit_trodan_RENEWAL').is(':checked') && 
                 !$('#gridit_trodan_DISABLEVOMS').is(':checked') 
             ) $('#gridit_trodan_OPTIONS').val('NULL');                 
                 
            if ( !$('#eumed_trodan_RENEWAL').is(':checked') && 
                  !$('#eumed_trodan_DISABLEVOMS').is(':checked') 
             ) $('#eumed_trodan_OPTIONS').val('NULL');
                 
            if ( !$('#biomed_trodan_RENEWAL').is(':checked') && 
                  !$('#biomed_trodan_DISABLEVOMS').is(':checked') 
             ) $('#biomed_trodan_OPTIONS').val('NULL');                              
       });                
    });    
    
    function resetTRODANSettings()
    {            
        $('#cometa_trodan_INFRASTRUCTURE').val('N/A');
        $('#cometa_trodan_TOPBDII').val('N/A');
        $('#cometa_trodan_WMS').val('N/A');
        $('#cometa_trodan_VONAME').val('N/A');        
        $('#cometa_trodan_ETOKENSERVER').val('N/A');
        $('#cometa_trodan_MYPROXYSERVER').val('N/A');
        $('#cometa_trodan_PORT').val('N/A');
        $('#cometa_trodan_ROBOTID').val('N/A');
        $('#cometa_trodan_ROLE').val('N/A');        
        $('input[type=\'checkbox\'][name=\'#cometa_trodan_RENEWAL\']').attr ('checked', true);
        $('input[type=\'checkbox\'][name=\'#cometa_trodan_DISABLEVOMS\']').attr ('checked', false);
        
        $('#gridit_trodan_INFRASTRUCTURE').val('N/A');
        $('#gridit_trodan_TOPBDII').val('N/A');
        $('#gridit_trodan_WMS').val('N/A');
        $('#gridit_trodan_VONAME').val('N/A');        
        $('#gridit_trodan_ETOKENSERVER').val('N/A');
        $('#gridit_trodan_MYPROXYSERVER').val('N/A');
        $('#gridit_trodan_PORT').val('N/A');
        $('#gridit_trodan_ROBOTID').val('N/A');
        $('#gridit_trodan_ROLE').val('N/A');        
        $('input[type=\'checkbox\'][name=\'#gridit_trodan_RENEWAL\']').attr ('checked', true);
        $('input[type=\'checkbox\'][name=\'#gridit_trodan_DISABLEVOMS\']').attr ('checked', false);        
                
        $('#eumed_trodan_INFRASTRUCTURE').val('N/A');
        $('#eumed_trodan_TOPBDII').val('N/A');
        $('#eumed_trodan_WMS').val('N/A');
        $('#eumed_trodan_VONAME').val('N/A');        
        $('#eumed_trodan_ETOKENSERVER').val('N/A');
        $('#eumed_trodan_MYPROXYSERVER').val('N/A');
        $('#eumed_trodan_PORT').val('N/A');
        $('#eumed_trodan_ROBOTID').val('N/A');
        $('#eumed_trodan_ROLE').val('N/A');        
        $('input[type=\'checkbox\'][name=\'#eumed_trodan_RENEWAL\']').attr ('checked', true);
        $('input[type=\'checkbox\'][name=\'#eumed_trodan_DISABLEVOMS\']').attr ('checked', false);
        
        $('#biomed_trodan_INFRASTRUCTURE').val('N/A');
        $('#biomed_trodan_TOPBDII').val('N/A');
        $('#biomed_trodan_WMS').val('N/A');
        $('#biomed_trodan_VONAME').val('N/A');        
        $('#biomed_trodan_ETOKENSERVER').val('N/A');
        $('#biomed_trodan_MYPROXYSERVER').val('N/A');
        $('#biomed_trodan_PORT').val('N/A');
        $('#biomed_trodan_ROBOTID').val('N/A');
        $('#biomed_trodan_ROLE').val('N/A');        
        $('input[type=\'checkbox\'][name=\'#biomed_trodan_RENEWAL\']').attr ('checked', true);
        $('input[type=\'checkbox\'][name=\'#biomed_trodan_DISABLEVOMS\']').attr ('checked', false);
        
        $("#trodan_ENABLEINFRASTRUCTURE").removeAttr("checked");
        $('#trodan_APPID').val('N/A');
        $('#trodan_SOFTWARE').val('N/A');
        $('#TRACKING_DB_HOSTNAME').val('N/A');
        $('#TRACKING_DB_USERNAME').val('N/A');
        $('#TRACKING_DB_PASSWORD').val('N/A');
        $('#SMTP_HOST').val('N/A');
        $('#SENDER_MAIL').val('N/A');
        return false;
    }
        
</script>

<br/>
<p style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
    Please, select the TRODAN Grid Settings before to start</p>  

<!DOCTYPE html>
<form id="TrodanEditForm"
      name="TrodanEditForm"
      action="<portlet:actionURL><portlet:param name="ActionEvent" value="CONFIG_TRODAN_PORTLET"/></portlet:actionURL>" 
      method="POST">

<fieldset>
<legend>Portlet Settings</legend>
<div style="margin-left:15px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;" id="error_message"></div>
<br/>
<table id="results" border="0" width="620" style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">

<!-- COMETA -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="trodan_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>
    
    <td>        
        <c:forEach var="enabled" items="<%= trodan_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='cometa'}">
                <c:set var="results_cometa" value="true"></c:set>
            </c:if>
        </c:forEach>
                
        <c:choose>
        <c:when test="${results_cometa=='true'}">
        <input type="checkbox" 
               id="cometa_trodan_ENABLEINFRASTRUCTURE"
               name="trodan_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="cometa"
               checked="checked"/>
        </c:when>
        <c:otherwise>
        <input type="checkbox" 
               id="cometa_trodan_ENABLEINFRASTRUCTURE"
               name="trodan_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="cometa"/>
        </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="cometa_trodan_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="cometa_trodan_INFRASTRUCTURE"
               name="cometa_trodan_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="COMETA" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="cometa_trodan_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="cometa_trodan_VONAME"
               name="cometa_trodan_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= cometa_trodan_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="cometa_trodan_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="cometa_trodan_TOPBDII"
               name="cometa_trodan_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= cometa_trodan_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"         
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="cometa_trodan_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>          
        <c:forEach var="wms" items="<%= cometa_trodan_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_cometaWMS">
            <input type="text"                
                   name="cometa_trodan_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_cometa2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WMS Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_cometa_trodan_WMS">
        <input type="text"                
               name="cometa_trodan_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_cometa" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WMS Endopoint" />
        <img type="button" class="btnDel_cometa" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WMS Endopoint" />
        </div>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="cometa_trodan_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="cometa_trodan_MYPROXYSERVER"
               name="cometa_trodan_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= cometa_trodan_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="cometa_trodan_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="cometa_trodan_ETOKENSERVER"
               name="cometa_trodan_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= cometa_trodan_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="cometa_trodan_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="cometa_trodan_PORT"
               name="cometa_trodan_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= cometa_trodan_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MetaData Server to be contacted" />
   
        <label for="cometa_trodan_METADATASERVER">MetaData Server</label>
    </td>
    <td>
        <input type="text" 
               id="cometa_trodan_METADATASERVER"
               name="cometa_trodan_METADATASERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= cometa_trodan_METADATASERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MetaData Server port" />
   
        <label for="cometa_trodan_METADATAPORT">Port</label>
    </td>
    <td>
        <input type="text" 
               id="cometa_trodan_METADATAPORT"
               name="cometa_trodan_METADATAPORT"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="15px;" 
               value=" <%= cometa_trodan_METADATAPORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="cometa_trodan_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="cometa_trodan_ROBOTID"
               name="cometa_trodan_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= cometa_trodan_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="cometa_trodan_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="cometa_trodan_ROLE"
               name="cometa_trodan_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= cometa_trodan_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="cometa_trodan_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="cometa_trodan_RENEWAL"
               name="cometa_trodan_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= cometa_trodan_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="cometa_trodan_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="cometa_trodan_DISABLEVOMS"
               name="cometa_trodan_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= cometa_trodan_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>
    
<!-- GRIDIT -->  
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="trodan_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        
        <c:forEach var="enabled" items="<%= trodan_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='gridit'}">
                <c:set var="results_gridit" value="true"></c:set>
            </c:if>
        </c:forEach>
        
        <c:choose>
        <c:when test="${results_gridit=='true'}">
        <input type="checkbox" 
               id="trodan_ENABLEINFRASTRUCTURE"
               name="trodan_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="gridit"               
               checked="checked"/>
        </c:when>
        <c:otherwise>
        <input type="checkbox" 
               id="trodan_ENABLEINFRASTRUCTURE"
               name="trodan_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="gridit"/>
        </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="gridit_trodan_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="gridit_trodan_INFRASTRUCTURE"
               name="gridit_trodan_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="GRIDIT" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="gridit_trodan_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="gridit_trodan_VONAME"
               name="gridit_trodan_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= gridit_trodan_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="gridit_trodan_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="gridit_trodan_TOPBDII"
               name="gridit_trodan_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gridit_trodan_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"         
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="gridit_trodan_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>          
        <c:forEach var="wms" items="<%= gridit_trodan_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_griditWMS">
            <input type="text"                
                   name="gridit_trodan_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_gridit2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WMS Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_gridit_trodan_WMS">
        <input type="text"                
               name="gridit_trodan_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_gridit" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WMS Endopoint" />
        <img type="button" class="btnDel_gridit" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WMS Endopoint" />
        </div>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="gridit_trodan_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gridit_trodan_MYPROXYSERVER"
               name="gridit_trodan_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gridit_trodan_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="gridit_trodan_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gridit_trodan_ETOKENSERVER"
               name="gridit_trodan_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gridit_trodan_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="gridit_trodan_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gridit_trodan_PORT"
               name="gridit_trodan_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= gridit_trodan_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MetaData Server to be contacted" />
   
        <label for="gridit_trodan_METADATASERVER">MetaData Server</label>
    </td>
    <td>
        <input type="text" 
               id="gridit_trodan_METADATASERVER"
               name="gridit_trodan_METADATASERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= gridit_trodan_METADATASERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MetaData Server port" />
   
        <label for="gridit_trodan_METADATAPORT">Port</label>
    </td>
    <td>
        <input type="text" 
               id="gridit_trodan_METADATAPORT"
               name="gridit_trodan_METADATAPORT"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="15px;" 
               value=" <%= gridit_trodan_METADATAPORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="gridit_trodan_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gridit_trodan_ROBOTID"
               name="gridit_trodan_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gridit_trodan_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="gridit_trodan_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="gridit_trodan_ROLE"
               name="gridit_trodan_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= gridit_trodan_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="gridit_trodan_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="gridit_trodan_RENEWAL"
               name="gridit_trodan_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= gridit_trodan_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="gridit_trodan_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="gridit_trodan_DISABLEVOMS"
               name="gridit_trodan_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= gridit_trodan_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- EUMED -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="trodan_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>
    
    <td>        
        <c:forEach var="enabled" items="<%= trodan_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='eumed'}">
                <c:set var="results_eumed" value="true"></c:set>
            </c:if>
        </c:forEach>
                
        <c:choose>
        <c:when test="${results_eumed=='true'}">
        <input type="checkbox" 
               id="trodan_ENABLEINFRASTRUCTURE"
               name="trodan_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="eumed"               
               checked="checked"/>
        </c:when>
        <c:otherwise>
        <input type="checkbox" 
               id="trodan_ENABLEINFRASTRUCTURE"
               name="trodan_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="eumed"/>
        </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="EUMED_trodan_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="eumed_trodan_INFRASTRUCTURE"
               name="eumed_trodan_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="EUMED" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="eumed_trodan_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="eumed_trodan_VONAME"
               name="eumed_trodan_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= eumed_trodan_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="eumed_trodan_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="eumed_trodan_TOPBDII"
               name="eumed_trodan_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_trodan_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="eumed_trodan_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>
        <c:forEach var="wms" items="<%= eumed_trodan_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_eumedWMS">
            <input type="text"                
                   name="eumed_trodan_WMS"
                   id="eumed_trodan_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_eumed2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WMS Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_eumed_trodan_WMS">
        <input type="text" 
               id="eumed_trodan_WMS"
               name="eumed_trodan_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_eumed" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WMS Endopoint" />
        <img type="button" class="btnDel_eumed" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WMS Endopoint" />
        </div>                     
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="eumed_trodan_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_trodan_MYPROXYSERVER"
               name="eumed_trodan_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_trodan_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="eumed_trodan_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_trodan_ETOKENSERVER"
               name="eumed_trodan_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_trodan_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="eumed_trodan_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_trodan_PORT"
               name="eumed_trodan_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= eumed_trodan_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MetaData Server to be contacted" />
   
        <label for="eumed_trodan_METADATASERVER">MetaData Server</label>
    </td>
    <td>
        <input type="text" 
               id="eumed_trodan_METADATASERVER"
               name="eumed_trodan_METADATASERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= eumed_trodan_METADATASERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MetaData Server port" />
   
        <label for="eumed_trodan_METADATAPORT">Port</label>
    </td>
    <td>
        <input type="text" 
               id="eumed_trodan_METADATAPORT"
               name="eumed_trodan_METADATAPORT"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="15px;" 
               value=" <%= eumed_trodan_METADATAPORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="eumed_trodan_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_trodan_ROBOTID"
               name="eumed_trodan_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_trodan_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="eumed_trodan_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="eumed_trodan_ROLE"
               name="eumed_trodan_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= eumed_trodan_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="eumed_trodan_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="eumed_trodan_RENEWAL"
               name="eumed_trodan_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= eumed_trodan_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="eumed_trodan_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="eumed_trodan_DISABLEVOMS"
               name="eumed_trodan_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= eumed_trodan_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- BIOMED -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acroym" />
   
        <label for="trodan_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>
    
    <td>
        <c:forEach var="enabled" items="<%= trodan_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='biomed'}">
                <c:set var="results_biomed" value="true"></c:set>
            </c:if>
        </c:forEach>
        
        <c:choose>
        <c:when test="${results_biomed=='true'}">
        <input type="checkbox" 
               id="trodan_ENABLEINFRASTRUCTURE"
               name="trodan_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="biomed"               
               checked="checked"/>
        </c:when>
        <c:otherwise>
        <input type="checkbox" 
               id="trodan_ENABLEINFRASTRUCTURE"
               name="trodan_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="biomed"/>
        </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="BIOMED_trodan_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="biomed_trodan_INFRASTRUCTURE"
               name="biomed_trodan_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="BIOMED" />
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="biomed_trodan_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="biomed_trodan_VONAME"
               name="biomed_trodan_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= biomed_trodan_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="biomed_trodan_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="biomed_trodan_TOPBDII"
               name="biomed_trodan_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= biomed_trodan_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="biomed_trodan_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>
        
        <c:forEach var="wms" items="<%= biomed_trodan_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_biomedWMS">
            <input type="text"                
                   name="biomed_trodan_WMS"
                   id="biomed_trodan_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_biomed2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WMS Endopoint" />
            </div>
            </c:if>
        </c:forEach>
        
        <div style="margin-bottom:4px;" class="cloned_biomed_trodan_WMS">
        <input type="text" 
               id="biomed_trodan_WMS"
               name="biomed_trodan_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_biomed" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WMS Endopoint" />
        <img type="button" class="btnDel_biomed" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WMS Endopoint" />
        </div>                     
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="biomed_trodan_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="biomed_trodan_MYPROXYSERVER"
               name="biomed_trodan_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= biomed_trodan_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="biomed_trodan_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="biomed_trodan_ETOKENSERVER"
               name="biomed_trodan_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= biomed_trodan_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="biomed_trodan_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="biomed_trodan_PORT"
               name="biomed_trodan_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= biomed_trodan_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MetaData Server to be contacted" />
   
        <label for="biomed_trodan_METADATASERVER">MetaData Server</label>
    </td>
    <td>
        <input type="text" 
               id="biomed_trodan_METADATASERVER"
               name="biomed_trodan_METADATASERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= biomed_trodan_METADATASERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MetaData Server port" />
   
        <label for="biomed_trodan_METADATAPORT">Port</label>
    </td>
    <td>
        <input type="text" 
               id="biomed_trodan_METADATAPORT"
               name="biomed_trodan_METADATAPORT"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="15px;" 
               value=" <%= biomed_trodan_METADATAPORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="biomed_trodan_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="biomed_trodan_ROBOTID"
               name="biomed_trodan_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= biomed_trodan_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="biomed_trodan_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="biomed_trodan_ROLE"
               name="biomed_trodan_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= biomed_trodan_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="biomed_trodan_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="biomed_trodan_RENEWAL"
               name="biomed_trodan_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= biomed_trodan_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="biomed_trodan_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="biomed_trodan_DISABLEVOMS"
               name="biomed_trodan_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= biomed_trodan_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- LAST -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The ApplicationID for TRODAN" />
   
        <label for="trodan_APPID">AppID<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="trodan_APPID"
               name="trodan_APPID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= trodan_APPID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Log Level of the portlet (E.g.: VERBOSE, INFO)" />
   
        <label for="trodan_LOGLEVEL">Log Level<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="trodan_LOGLEVEL"
               name="trodan_LOGLEVEL"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= trodan_LOGLEVEL %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The output path of the server's file-system where download results" />
   
        <label for="trodan_OUTPUT_PATH">Output Path<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="trodan_OUTPUT_PATH"
               name="trodan_OUTPUT_PATH"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= trodan_OUTPUT_PATH %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MetaData Repository" />
   
        <label for="trodan_REPOSITORY">Repository</label>
    </td>
    <td>
        <input type="text" 
               id="trodan_REPOSITORY"
               name="trodan_REPOSITORY"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= trodan_REPOSITORY %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Application Software TAG" />
   
        <label for="trodan_SOFTWARE">SoftwareTAG</label>
    </td>
    <td>
        <input type="text" 
               id="trodan_SOFTWARE"
               name="trodan_SOFTWARE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= trodan_SOFTWARE %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Tracking DB Server Hostname" />
   
        <label for="TRACKING_DB_HOSTNAME">HostName</label>
    </td>
    <td>
        <input type="text" 
               id="TRACKING_DB_HOSTNAME"
               name="TRACKING_DB_HOSTNAME"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= TRACKING_DB_HOSTNAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The username credential for login the Tracking DB" />
   
        <label for="TRACKING_DB_USERNAME">UserName</label>
    </td>
    <td>
        <input type="text" 
               id="TRACKING_DB_USERNAME"
               name="TRACKING_DB_USERNAME"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= TRACKING_DB_USERNAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The password credential for login  the Tracking DB" />
   
        <label for="TRACKING_DB_PASSWORD">Password</label>
    </td>
    <td>
        <input type="password" 
               id="TRACKING_DB_PASSWORD"
               name="TRACKING_DB_PASSWORD"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= TRACKING_DB_PASSWORD %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The SMTP Server for sending notification" />
   
        <label for="SMTP_HOST">SMTP</label>
    </td>
    <td>
        <input type="text" 
               id="SMTP_HOST"
               name="SMTP_HOST"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= SMTP_HOST %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The email address for sending notification" />
   
        <label for="Sender">Sender</label>
    </td>
    <td>
        <input type="text" 
               id="SENDER_MAIL"
               name="SENDER_MAIL"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= SENDER_MAIL %>" />
    </td>    
</tr>

<!-- Buttons -->
<tr>            
    <tr><td>&nbsp;</td></tr>
    <td align="left">    
    <input type="image" src="<%= renderRequest.getContextPath()%>/images/save.png"
           width="50"
           name="Submit" title="Save the portlet settings" />        
    </td>
</tr>  

</table>
<br/>
<div id="pageNavPosition" style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">   
</div>
</fieldset>
           
<script type="text/javascript">
    var pager = new Pager('results', 15); 
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
</script>
</form>
