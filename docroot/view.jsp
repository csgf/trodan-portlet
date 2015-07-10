<%
/**************************************************************************
Copyright (c) 2011-2014:
Istituto Nazionale di Fisica Nucleare (INFN), Italy
Consorzio COMETA (COMETA), Italy
    
See http://www.infn.it and and http://www.consorzio-cometa.it for details 
on the copyright holders.
    
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
**************************************************************************/
%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.liferay.portal.model.Company" %>
<%@ page import="javax.portlet.*" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<%
  Company company = PortalUtil.getCompany(request);
  String gateway = company.getName();
%>

<jsp:useBean id="GPS_table" class="java.lang.String" scope="request"/>
<jsp:useBean id="GPS_queue" class="java.lang.String" scope="request"/>

<jsp:useBean id="cometa_trodan_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_trodan_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gridit_trodan_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_trodan_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="eumed_trodan_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_trodan_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="biomed_trodan_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_trodan_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="trodan_ENABLEINFRASTRUCTURE" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="trodan_APPID" class="java.lang.String" scope="request"/>
<jsp:useBean id="trodan_OUTPUT_PATH" class="java.lang.String" scope="request"/>
<jsp:useBean id="trodan_SOFTWARE" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_HOSTNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_USERNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_PASSWORD" class="java.lang.String" scope="request"/>
<jsp:useBean id="SMTP_HOST" class="java.lang.String" scope="request"/>
<jsp:useBean id="SENDER_MAIL" class="java.lang.String" scope="request"/>
<jsp:useBean id="trodan_STATIONS" type="java.util.List<String>" class="java.util.ArrayList<String>" scope="request"/>

<script type="text/javascript">
    var latlng2markers = [];
    var icons = [];
    
    icons["plus"] = new google.maps.MarkerImage(
          '<%= renderRequest.getContextPath()%>/images/plus_new.png',
          new google.maps.Size(16,16),
          new google.maps.Point(0,0),
          new google.maps.Point(8,8));
    
    icons["minus"] = new google.maps.MarkerImage(
          '<%= renderRequest.getContextPath()%>/images/minus_new.png',
          new google.maps.Size(16,16),
          new google.maps.Point(0,0),
          new google.maps.Point(8,8));
    
    icons["ce"] = new google.maps.MarkerImage(
            '<%= renderRequest.getContextPath()%>/images/ce-run.png',
            new google.maps.Size(16,16),
            new google.maps.Point(0,0),
            new google.maps.Point(8,8));
    
    function hideMarkers(markersMap,map) 
    {
            for( var k in markersMap) 
            {
                if (markersMap[k].markers.length >1) {
                    var n = markersMap[k].markers.length;
                    var centerMark = new google.maps.Marker({
                        title: "Coordinates:" + markersMap[k].markers[0].getPosition().toString(),
                        position: markersMap[k].markers[0].getPosition(),
                        icon: icons["plus"]
                    });
                    for ( var i=0 ; i<n ; i++ ) 
                        markersMap[k].markers[i].setVisible(false);
                    
                    centerMark.setMap(map);
                    google.maps.event.addListener(centerMark, 'click', function() {
                        var markersMap = latlng2markers;
                        var k = this.getPosition().toString();
                        var visibility = markersMap[k].markers[0].getVisible();
                        if (visibility == false ) {
                            splitMarkersOnCircle(markersMap[k].markers,
                            markersMap[k].connectors,
                            this.getPosition(),
                            map
                        );
                            this.setIcon(icons["minus"]);
                        }
                        else {
                            var n = markersMap[k].markers.length;
                            for ( var i=0 ; i<n ; i++ ) {
                                markersMap[k].markers[i].setVisible(false);
                                markersMap[k].connectors[i].setMap(null);
                            }
                            markersMap[k].connectors = [];
                            this.setIcon(icons["plus"]);
                        }
                    });
                }
            }
    }
    
    function splitMarkersOnCircle(markers, connectors, center, map) 
    {
            var z = map.getZoom();
            var r = 64.0 / (z*Math.exp(z/2));
            var n = markers.length;
            var dtheta = 2.0 * Math.PI / n
            var theta = 0;
            
            for ( var i=0 ; i<n ; i++ ) 
            {
                var X = center.lat() + r*Math.cos(theta);
                var Y = center.lng() + r*Math.sin(theta);
                markers[i].setPosition(new google.maps.LatLng(X,Y));
                markers[i].setVisible(true);
                theta += dtheta;
                
                var line = new google.maps.Polyline({
                    path: [center,new google.maps.LatLng(X,Y)],
                    clickable: false,
                    strokeColor: "#0000ff",
                    strokeOpacity: 1,
                    strokeWeight: 2
                });
                
                line.setMap(map);
                connectors.push(line);
            }
    }
    
    function updateAverage(name) {
        
        $.getJSON('<portlet:resourceURL><portlet:param name="action" value="get-ratings"/></portlet:resourceURL>&trodan_CE='+name,
        function(data) {                                               
            console.log(data.avg);
            $("#fake-stars-on").width(Math.round(parseFloat(data.avg)*20)); // 20 = 100(px)/5(stars)
            $("#fake-stars-cap").text(new Number(data.avg).toFixed(2) + " (" + data.cnt + ")");
        });                
    }
    
    // Create the Google Map JavaScript APIs V3
    function initialize(lat, lng, zoom) {
        console.log(lat);
        console.log(lng);
        console.log(zoom);
        
        var myOptions = {
            zoom: zoom,
            center: new google.maps.LatLng(lat, lng),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        
        var map = new google.maps.Map(document.getElementById('map_canvas'), myOptions);  
        var image = new google.maps.MarkerImage('<%= renderRequest.getContextPath() %>/images/ce-run.png');
        
        var strVar="";
        strVar += "<table>";
        strVar += "<tr>";
        strVar += "<td>";
        strVar += "Vote the resource availability";
        strVar += "<\/td>";
        strVar += "<\/tr>";
        strVar += "<tr><td>\&nbsp;\&nbsp;";
        strVar += "<\/td><\/tr>";
        
        strVar += "<tr>";
        strVar += "<td>";
        strVar += "Rating: <span id=\"stars-cap\"><\/span>";
        strVar += "<div id=\"stars-wrapper2\">";
        strVar += "<select name=\"selrate\">";
        strVar += "<option value=\"1\">Very poor<\/option>";
        strVar += "<option value=\"2\">Not that bad<\/option>";
        strVar += "<option value=\"3\" selected=\"selected\">Average<\/option>";
        strVar += "<option value=\"4\">Good<\/option>";
        strVar += "<option value=\"5\">Perfect<\/option>";
        strVar += "<\/select>";
        strVar += "<\/div>";
        strVar += "<\/td>";
        strVar += "<\/tr>";

        strVar += "<tr>";        
        strVar += "<td>";
        strVar += "Average: <span id=\"fake-stars-cap\"><\/span>";
        strVar += "";
        strVar += "<div id=\"fake-stars-off\" class=\"stars-off\" style=\"width:100px\">";
        strVar += "<div id=\"fake-stars-on\" class=\"stars-on\"><\/div>";
        strVar += "";
        strVar += "<\/div>";
        strVar += "<\/td>";
        strVar += "<\/tr>";
        strVar += "<\/table>";
    
        var data = <%= GPS_table %>;
        var queues = <%= GPS_queue %>;
        
        var infowindow = new google.maps.InfoWindow();
        google.maps.event.addListener(infowindow, 'closeclick', function() {
            this.setContent('');
        });
        
        var infowindowOpts = { 
            maxWidth: 200
        };
               
       infowindow.setOptions(infowindowOpts);       
        
        var markers = [];
        for( var key in data ){
                        
            var LatLong = new google.maps.LatLng(parseFloat(data[key]["LAT"]), 
                                                 parseFloat(data[key]["LNG"]));                    
            
            // Identify locations on the map
            var marker = new google.maps.Marker ({
                animation: google.maps.Animation.DROP,
                position: LatLong,
                icon: image,
                title : key
            });    
            
            // Add the market to the map
            marker.setMap(map);
  
            var latlngKey=marker.position.toString();
            if ( latlng2markers[latlngKey] == null )
                latlng2markers[latlngKey] = {markers:[], connectors:[]};
            
            latlng2markers[latlngKey].markers.push(marker);
            markers.push(marker);                         
        
            google.maps.event.addListener(marker, 'click', function() {
             
            var ce_hostname = this.title;
            
            google.maps.event.addListenerOnce(infowindow, 'domready', function() {
                                        
                    $("#stars-wrapper2").stars({
                        cancelShow: false, 
                        oneVoteOnly: true,
                        inputType: "select",
                        callback: function(ui, type, value)
                        { 
                            $.getJSON('<portlet:resourceURL><portlet:param name="action" value="set-ratings"/></portlet:resourceURL>' +
                                '&trodan_CE=' + ce_hostname + 
                                '&vote=' + value);
                            
                            updateAverage(ce_hostname);                      
                        }
                    });
                    
                    updateAverage(ce_hostname);               
                });              
                                                
                infowindow.setContent('<h3>' + ce_hostname + '</h3><br/>' + strVar);
                infowindow.open(map, this);
                                           
                var CE_queue = (queues[ce_hostname]["QUEUE"]);
                $('#trodan_CE').val(CE_queue);
                                
                marker.setMap(map);
            }); // function                             
        } // for
        
        hideMarkers(latlng2markers,map);
        var markerCluster = new MarkerClusterer(map, markers, {maxZoom: 3, gridSize: 20});
    }    
</script>

<script type="text/javascript">
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
    //console.log (NMAX);   

    $(document).ready(function() 
    {
        // Toggling the hidden div for the first time.        
        if ($('#divToToggle').css('display') == 'block')
            $('#divToToggle').toggle();
        
       $(function() {
            $( "#trodan_range_from" ).datepicker({
                defaultDate: "+1w",
                dateFormat: "dd/mm/yy",                
                minDate: new Date (2007, 6, 31),
                changeMonth: true,
                changeYear: true,
                numberOfMonths: 3,
                onClose: function( selectedDate ) {
                    $( "#trodan_range_to" ).datepicker( "option", "minDate", selectedDate );
                }
            });
            
            $( "#trodan_range_to" ).datepicker({
                defaultDate: "+1w",
                dateFormat: "dd/mm/yy",                
                maxDate: new Date (2013, 4, 21),               
                changeMonth: true,
                changeYear: true,
                numberOfMonths: 3,
                onClose: function( selectedDate ) {
                    $( "#trodan_range_from" ).datepicker( "option", "maxDate", selectedDate );
                }
            });
        }); 
        
        var lat; var lng; var zoom;
        var found=0;
        
        if (parseInt(NMAX)>1) { 
            console.log ("More than one infrastructure has been configured!");
            $("#error_infrastructure img:last-child").remove();
            lat=19;lng=14;zoom=2; found=1;
        } else if (EnabledInfrastructure=='cometa') {
            console.log ("Start up: enabled the cometa VO!");
            $('#cometa_trodan_ENABLEINFRASTRUCTURE').attr('checked','checked');
            lat=37,57;lng=14,28;zoom=7;
            found=1;
        } else if (EnabledInfrastructure=='gridit') {
            console.log ("Start up: enabled the gridit VO!");
            $('#gridit_trodan_ENABLEINFRASTRUCTURE').attr('checked','checked');
            lat=42;lng=12;zoom=5;
            found=1;
        } else if (EnabledInfrastructure=='eumed') {
            console.log ("Start up: enabled the eumed VO!");
            $('#eumed_trodan_ENABLEINFRASTRUCTURE').attr('checked','checked');
            lat=34;lng=20;zoom=4;
            found=1;
        } else if (EnabledInfrastructure=='biomed') {
            console.log ("Start up: enabled the biomed VO!");
            $('#biomed_trodan_ENABLEINFRASTRUCTURE').attr('checked','checked');
            lat=48;lng=16;zoom=4;
            found=1;
        }
        
        if (found==0) { 
            console.log ("None of the grid infrastructures have been configured!");
            $("#error_infrastructure img:last-child").remove();
            $('#error_infrastructure')
            .append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> None of the available grid infrastructures have been configured!");
        }
        
        var accOpts = {
            change: function(e, ui) {                       
                $("<div style='width:650px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;'>").addClass("notify ui-corner-all").text(ui.newHeader.find("a").text() +
                    " was activated... ").appendTo("#error_message").fadeOut(2500, function(){ $(this).remove(); });               
                // Get the active option                
                var active = $("#accordion").accordion("option", "active");
                if (active==1) initialize(lat, lng, zoom);
            },
            autoHeight: false
        };
        
        // Create the accordions
        //$("#accordion").accordion({ autoHeight: false });
        $("#accordion").accordion(accOpts);
        
        $("#commentForm").bind('submit', function() {        
                        
            var flag=true;
            var trodan_SELECTED_STATIONS = new Array();
            
            // Remove the img and text error (if any)
            $("#error_message img:last-child").remove();
            $("#error_message").empty();
            
            // A.) Check if Metereological Stations(s) have been selected
            $('input[name="selectedStations"]:checked').each(function() { 
                trodan_SELECTED_STATIONS.push(this.value);                 
            });                         
            
            if (trodan_SELECTED_STATIONS.length==0) {
                // Display the warning message                
                $('#error_message').append("Meteorological Station(s) missing!");
                $("#error_message").css({"color":"red","font-size":"14px"});
                console.log("Metereological Station(s) missing!");
                return false;
                flag=false;
            } else console.log(trodan_SELECTED_STATIONS);
            
            // B.) Check if Metereological Pattern(s) have been selected
            if ( (!$('#EnableAirTemperature').attr('checked')) &&
                 (!$('#EnableRelativeHumidity').attr('checked')) &&
                 (!$('#EnablePrecipitation').attr('checked')) &&
                 (!$('#EnableSoilTemperature').attr('checked')) &&
                 (!$('#EnableSolarRadiation').attr('checked')) &&
                 (!$('#EnableWindSpeed').attr('checked')) &&
                 (!$('#EnableBattVolt').attr('checked')) &&
                 (!$('#EnableSlrW_avg').attr('checked')) &&
                 (!$('#EnableT107_C_Avg').attr('checked')) &&
                 (!$('#EnableWindDir').attr('checked')) &&
                 (!$('#EnableBarPress').attr('checked')) &&
                 (!$('#EnableVW').attr('checked')) &&
                 (!$('#EnablePA_uS').attr('checked'))
               ) {
                    // Display the warning message  
                    $('#error_message').append("Meteorological Pattern(s) missing!");
                    $("#error_message").css({"color":"red","font-size":"14px"});
                    console.log("Metereological Patterns missing!");
                    return false;
                    flag=false;
               }
               
             // C.) Check if the date range is null
             if (($('#trodan_range_from').val()=="") || ($('#trodan_range_to').val()==""))
             {
                     // Display the warning message  
                    $('#error_message').append("Date range is missing!");
                    $("#error_message").css({"color":"red","font-size":"14px"});
                    console.log("Metereological Patterns missing!");
                    return false;
                    flag=false;
             }                          
                
            if (flag) { 
                $( "#dialog-message" ).append("Thanks for submitting a new request! <br/><br/>Your request is going to be processed by the Gateway.");
                $( "#dialog-message" ).dialog({
                modal: true,
                title: "TRODAN Notification",                
                height: 150,
                width: 300
                /*buttons: { Ok: function() { $( this ).dialog( "close" ); } }*/
                });
            
                $("#error_message").css({"color":"red","font-size":"14px", "font-family": "Tahoma,Verdana,sans-serif,Arial"}); 
                $('#error_message').append("Submission in progress...")(30000, function(){ $(this).remove(); });                               
            }                        
        });
        
        // Roller
        $('#trodan_footer').rollchildren({
            delay_time         : 3000,
            loop               : true,
            pause_on_mouseover : true,
            roll_up_old_item   : true,
            speed              : 'slow'   
        });
        
        $("#stars-wrapper1").stars({
            cancelShow: false,
            captionEl: $("#stars-cap"),
            callback: function(ui, type, value)
            {
                $.getJSON("ratings.php", {rate: value}, function(json)
                {                                        
                    $("#fake-stars-on").width(Math.round( $("#fake-stars-off").width()/ui.options.items*parseFloat(json.avg) ));
                    $("#fake-stars-cap").text(json.avg + " (" + json.votes + ")");
                });
            }
        });
    });
    
    function ToggleMeteorologicalPatterns()
    {
        if ( ($('#TogglePatterns:checked').val() != undefined ) ) {
              console.log("Toggling Patterns ON");
              $("#EnablePrecipitation").attr('checked','checked');
              $("#EnableSolarRadiation").attr('checked','checked');
              $("#EnableAirTemperature").attr('checked','checked');
              $("#EnableRelativeHumidity").attr('checked','checked');
              $("#EnableSoilTemperature").attr('checked','checked');
              $("#EnableWindSpeed").attr('checked','checked');
              $("#EnableWindDir").attr('checked','checked');
              $("#EnableBarPressure").attr('checked','checked');
              $("#EnableVW").attr('checked','checked');
              $("#EnablePA_uS").attr('checked','checked');
         } else {
             console.log("Toggling Patterns OFF");
              $("#EnablePrecipitation").removeAttr('checked');
              $("#EnableSolarRadiation").removeAttr('checked');
              $("#EnableAirTemperature").removeAttr('checked');
              $("#EnableRelativeHumidity").removeAttr('checked','checked');
              $("#EnableSoilTemperature").removeAttr('checked','checked');
              $("#EnableWindSpeed").removeAttr('checked','checked');
              $("#EnableWindDir").removeAttr('checked','checked');
              $("#EnableBarPressure").removeAttr('checked','checked');
              $("#EnableVW").removeAttr('checked','checked');
              $("#EnablePA_uS").removeAttr('checked','checked');
          }
    }
    
    function ToggleTrodanStations()
    {
        if ( ($('#ToggleStations:checked').val() != undefined ) ) {
            console.log("Toggling Stations ON");
            $("#Abuja").attr('checked','checked');
            $("#Akungba").attr('checked','checked');
            $("#Akure").attr('checked','checked');
            $("#Anyigba").attr('checked','checked');
            $("#Eburumili").attr('checked','checked');
            $("#Redeemers").attr('checked','checked');
            $("#Lapai").attr('checked','checked');
            $("#Lagos").attr('checked','checked');
            $("#Nsukka").attr('checked','checked');
            $("#Ogbomoso").attr('checked','checked');
            $("#Makurdi").attr('checked','checked');
            $("#Minna").attr('checked','checked');
            $("#Porthacourt").attr('checked','checked');
            $("#Yola").attr('checked','checked');
        }
        else {
            console.log("Toggling Stations OFF");
            $("#Abuja").removeAttr('checked','checked');
            $("#Akungba").removeAttr('checked','checked');
            $("#Akure").removeAttr('checked','checked');
            $("#Anyigba").removeAttr('checked','checked');
            $("#Eburumili").removeAttr('checked','checked');
            $("#Redeemers").removeAttr('checked','checked');
            $("#Lapai").removeAttr('checked','checked');
            $("#Lagos").removeAttr('checked','checked');
            $("#Nsukka").removeAttr('checked','checked');
            $("#Ogbomoso").removeAttr('checked','checked');
            $("#Makurdi").removeAttr('checked','checked');
            $("#Minna").removeAttr('checked','checked');
            $("#Porthacourt").removeAttr('checked','checked');
            $("#Yola").removeAttr('checked','checked');
        }
    }
    
    function toggleAndChangeText() {        
        
        if ($('#divToToggle').css('display') == 'none') {
            // Collapse the div
            /*$('#aTag').html('Advanced plot options &#9658');
            if ($("select option:selected").val()=="ASCIItoMIDI") {
                console.log("Configure advanced settings for the algorithm ASCIItoMIDI");               
                $('#AdvancedOption_Toggle').show();
                $('#DATASETtoMIDI_Toggle').hide();
                $('#DATASETtoWAVE_Toggle').hide();
            }
            if ($("select option:selected").val()=="DATASETtoMIDI") {
                console.log("Configure advanced settings for the algorithm DATASETtoMIDI");
                $('#DATASETtoMIDI_Toggle').show();
                $('#AdvancedOption_Toggle').hide();  
                $('#DATASETtoWAVE_Toggle').hide();
            }
            if ($("select option:selected").val()=="ASCIItoSPHERE") {
                console.log("Configure advanced settings for the algorithm ASCIItoSHERE");
                $('#DATASETtoMIDI_Toggle').hide();
                $('#AdvancedOption_Toggle').hide();
                $('#DATASETtoWAVE_Toggle').hide();
            }
            if ($("select option:selected").val()=="DATASETtoWAVE") {                
                console.log("Configure advanced settings for the algorithm DATASETtoWAVE");
                $('#DATASETtoWAVE_Toggle').show();
                $('#DATASETtoMIDI_Toggle').hide();
                $('#AdvancedOption_Toggle').hide();
            }*/
        } else 
            // Expand the div
            $('#aTag').html('Advanced plot options &#9660');
        
        $('#divToToggle').toggle();
    }
</script>

<br/>
<div id="dialog-message" title="Notification">
<p></p>
</div>

<form id="commentForm" 
      action="<portlet:actionURL><portlet:param name="ActionEvent" 
      value="SUBMIT_TRODAN_PORTLET"/></portlet:actionURL>"      
      method="POST">
      <fieldset>
<legend>Trodan Input Form</legend>
<div style="margin-left:15px" id="error_message"></div>

<!-- Accordions -->
<div id="accordion" style="width:650px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
<h3><a href="#">
    <img width="32" src="<%=renderRequest.getContextPath()%>/images/glass_numbers_1.png" />
    <b>Portlet Settings</b>
    <img width="45" align="absmiddle" 
         src="<%=renderRequest.getContextPath()%>/images/info_image.png"/>
    </a>
</h3>
<div> <!-- Inizio primo accordion -->
<p>The portlet is currently configured to access the following e-Infrastructure(s):</p>
<table id="results" border="0" width="600">
    
<!-- COMETA -->
<tr></tr>
<tr>
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
               name="cometa_trodan_ENABLEINFRASTRUCTURE"
               size="55px;"
               checked="checked"
               class="textfield ui-widget ui-widget-content required"
               disabled="disabled"/> The COMETA Grid Infrastructure        
        </c:when>
        </c:choose>
    </td>
</tr>

<!-- EUMED -->
<tr></tr>
<tr>
    <td>
        <c:forEach var="enabled" items="<%= trodan_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='eumed'}">
                <c:set var="results_eumed" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
        <c:when test="${results_eumed=='true'}">
        <input type="checkbox" 
               id="eumed_trodan_ENABLEINFRASTRUCTURE"
               name="eumed_trodan_ENABLEINFRASTRUCTURE"
               size="55px;"
               checked="checked"
               class="textfield ui-widget ui-widget-content required"
               disabled="disabled"/> The EUMEDGRID-Support Computing e-Infrastructure               
        </c:when>
        </c:choose>
    </td>
</tr>

<!-- GRIDIT -->
<tr></tr>
<tr>
    <td>
        <c:forEach var="enabled" items="<%= trodan_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='gridit'}">
                <c:set var="results_gridit" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
        <c:when test="${results_gridit=='true'}">
        <input type="checkbox" 
               id="gridit_trodan_ENABLEINFRASTRUCTURE"
               name="gridit_trodan_ENABLEINFRASTRUCTURE"
               size="55px;"
               checked="checked"
               class="textfield ui-widget ui-widget-content required"
               disabled="disabled"/> The Italian Grid Infrastructure
        </c:when>
        </c:choose>
    </td>
</tr>

<!-- BIOMED -->
<tr></tr>
<tr>
    <td>
        <c:forEach var="enabled" items="<%= trodan_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='biomed'}">
                <c:set var="results_biomed" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
        <c:when test="${results_biomed=='true'}">
        <input type="checkbox"
               id="biomed_trodan_ENABLEINFRASTRUCTURE"
               name="biomed_trodan_ENABLEINFRASTRUCTURE"
               size="55px;"
               checked="checked"
               class="textfield ui-widget ui-widget-content required"
               disabled="disabled" /> The BIOMED Grid Infrastructure
        </c:when>
        </c:choose>
    </td>
</tr>
</table>
<br/>
<div style="margin-left:15px" 
     id="error_infrastructure" 
     style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px; display:none;">    
</div>
<br/>

<p align="center">
<img width="120" src="<%=renderRequest.getContextPath()%>/images/separatore.gif"/>
</p>

<p align="justify">
Instructions for users:<br/>
~ This portlet aims to analyze meteorological patterns measured from different Trodan Stations.<br/>

<table id="results" border="0">
<tr>
<td>
<b>About Trodan:</b>
<p align="justify">   
~ The <a href="http://www.carnasrda.com/">Centre for Atmospheric Research</a> (CAR) is an activity Centre of the 
<a href="http://nasrda.gov.ng/en/portal/">Nigerian National Space Research and Development Agency</a>, NASRDA, 
committed to research and capacity building in the atmospheric and related sciences. 
<p/>
</td>
<td>&nbsp;&nbsp;</td>
<td width="100">
<a href="http://www.gnu.org/software/octave/">
<img width="100" src="<%=renderRequest.getContextPath()%>/images/NASRDA_logo.png"/>
</a>
</td>
</tr>
</table>

<table id="results" border="0">
<tr>
<td width="700">
<p align="justify">
CAR is dedicated to understanding the atmosphere the air around us and the interconnected processes that make up 
the Earth system, from the ocean floor through the ionosphere to the Sun's core. <br/><br/>
The NASRDA Center for Atmospheric Research provides research facilities and services for the atmospheric and Earth 
Sciences community. <br/><br/>
Tropospheric Data Acquisition Network, TRODAN, is a project that was designed to monitor the lower atmosphere which 
covers region from the surface of the Earth to the altitude of about 11 km. <br/><br/>
This project is designed to collect and provide real-time meteorological data from different locations across Nigeria 
using for the purpose of research and development. <br/><br/>
At moment TRODAN equipment include atmospheric monitoring facilities such as Automatic Weather Stations, Micro Rain Radar 
facilities and Vantage Pro. This present data is obtained using Campbell Scientific Automatic Weather Station.<br/><br/>
<a href="http://www.gnuplot.info/">Gnuplot</a> is the portable command-line driven graphing utility for Linux, OS/2, 
MS Windows, OSX, VMS, and many other platforms used to visualize the Trodan Data Repository.<p/>
</td>
</tr>
</table>

<img width="20" src="<%=renderRequest.getContextPath()%>/images/help.png" title="Read the Help"/>
For further details, please click
<a href="<portlet:renderURL portletMode='HELP'><portlet:param name='action' value='./help.jsp' />
         </portlet:renderURL>" >here</a>
<br/>
</p>
  
The portlet takes as input:<br/>
~ the list of TRODAN Meteorological Stations to be considered;<br/>
~ the list of Meteorological Variables to be analyzed;<br/>
~ the plot style used by gnuplot;<br/>
~ the date range [min, max].<br/><br/>

Each run will produce:<br/>
~ std.txt: the standard output file;<br/>
~ std.err: the standard error file;<br/>
~ results.tar.gz: containing the results of the analysis.<br/>

For further information, please refer to the output.README file produced during the run.<br/><br/>

<p>If you need to change some preferences, please contact the
<a href="mailto:credentials-admin@ct.infn.it?Subject=Request for Technical Support [<%=gateway%> Science Gateway]&Body=Describe Your Problems&CC=sg-licence@ct.infn.it"> administrator</a>
</p>

<liferay-ui:ratings
    className="<%= it.infn.ct.trodan.Trodan.class.getName()%>"
    classPK="<%= request.getAttribute(WebKeys.RENDER_PORTLET).hashCode()%>" />

<!--div id="pageNavPosition"></div-->
</div> <!-- Fine Secondo Accordion -->

<h3><a href="#">
    <img width="32" src="<%=renderRequest.getContextPath()%>/images/glass_numbers_2.png" />
    <b>The Computing e-Infrastructure</b>
    <img width="40" align="absmiddle" 
         src="<%=renderRequest.getContextPath()%>/images/clouds.png"/>
    </a>
</h3>           
<div> <!-- Inizio Terzo accordion -->
    <p align="justify">
    See with the Google Map API where the software has been successfully installed.
    Select the GPS location of the grid resource where you want run your demo
    <u>OR, BETTER,</u> let the Science Gateway to choose the best one for you!<br/>
    This option is <u>NOT SUPPORTED</u> if more than one infrastructure is enabled!
    </p>

    <table border="0">
        <tr>
            <td><legend>Legend</legend></td>
            <td>&nbsp;<img src="<%=renderRequest.getContextPath()%>/images/plus_new.png"/></td>
            <td>&nbsp;Split close sites&nbsp;</td>
        
            <td><img src="<%=renderRequest.getContextPath()%>/images/minus_new.png"/></td>
            <td>&nbsp;Unsplit close sites&nbsp;</td>
            
            <td><img src="<%=renderRequest.getContextPath()%>/images/ce-run.png"/></td>
            <td>&nbsp;Computing resource&nbsp;</td>
        </tr>    
        <tr><td>&nbsp;</td></tr>
    </table>

    <legend>
        <div id="map_canvas" style="width:570px; height:600px;"></div>
    </legend>

    <input type="hidden" 
           name="trodan_CE" 
           id="trodan_CE"
           size="25px;" 
           class="textfield ui-widget ui-widget-content"
           value=""/>                  
</div> <!-- Fine Secondo Accordion -->        

<h3><a href="#">
    <img width="32" src="<%=renderRequest.getContextPath()%>/images/glass_numbers_3.png" />
    <b>Specify your Input Settings</b>
    <img width="40" align="absmiddle" 
         src="<%=renderRequest.getContextPath()%>/images/icon_small_settings.png"/>
    </a>
</h3>           
<div> <!-- Inizio Quarto accordion -->
    
<p><em>*</em> These fields are required</p>             
<p>A.) Specify the Meteorological Station(s) you want to analyze <em>*</em></p>
<table border="0" alignment="justify">
<% for(String station : trodan_STATIONS) { %>
<tr>
    <td width="350">
        <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png" 
         border="0" title="Enable the Meteorological Stations"/>
        
        <input type="checkbox" 
               name="selectedStations"
               id="<%= station %>"
               value="<%= station %>"/> <%= station %> 
        <br/>
    </td>
</tr>
<%}%>
<tr>
    <td width="350">
      <img width="30" 
           align="absmiddle"
           src="<%= renderRequest.getContextPath()%>/images/info_image.png" 
           border="0" title="Toggle Trodan Station(s)"/>
      
      <input type="checkbox" 
             name="ToggleStations" 
             id="ToggleStations" 
             value="checked" 
             onChange="ToggleTrodanStations();"/> Toggle Station(s)
      </td>
</tr>
</table>

<p align="center">
<img width="120" src="<%=renderRequest.getContextPath()%>/images/separatore.gif"/>
</p>
             
<br/><p>B.) Specify the Meteorological Pattern(s) you want to analyze <em>*</em></p>
<table border="0" alignment="justify">
 <tr>
     <td width="350">
     <img width="30" 
          align="absmiddle"
          src="<%= renderRequest.getContextPath()%>/images/question.png" 
          border="0" title="Enable the Precipitation"/>
                                       
      <input type="checkbox" 
             name="EnablePrecipitation"
             id="EnablePrecipitation" /> Rain Rate (mm)
      </td>
      <td width="350">
      <img width="30" 
           align="absmiddle"
           src="<%= renderRequest.getContextPath()%>/images/question.png" 
           border="0" title="Enable the Solar Radiation"/>
                                       
      <input type="checkbox" 
             name="EnableSolarRadiation"
             id="EnableSolarRadiation" /> Solar Radiation (W/m&#178;)
      </td>
</tr>
<tr>
    <td width="350">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png" 
         border="0" title="Enable the Air Temperature"/>
                                       
    <input type="checkbox" 
           name="EnableAirTemperature"
           id="EnableAirTemperature" /> Air Temperature (&#8451;)
    </td>               
    <td width="350">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png" 
         border="0" title="Enable the Relative Humidity"/>
                                       
     <input type="checkbox" 
            name="EnableRelativeHumidity"
            id="EnableRelativeHumidity" /> Relative Humidity (%)
     </td>               
</tr>
<tr>                      
      <td width="350">
      <img width="30" 
           align="absmiddle"
           src="<%= renderRequest.getContextPath()%>/images/question.png" 
           border="0" title="Enable the Soil Temperature"/>
                                       
      <input type="checkbox" 
             name="EnableSoilTemperature"
             id="EnableSoilTemperature" /> Soil Temperature (&#8451;)
      </td>               
      <td width="350">
      <img width="30" 
           align="absmiddle"
           src="<%= renderRequest.getContextPath()%>/images/question.png" 
           border="0" title="Enable the Wind Speed"/>
                                       
      <input type="checkbox" 
             name="EnableWindSpeed"
             id="EnableWindSpeed" /> Wind Speed (m/s)
      </td>               
</tr>
<tr>      
      <td width="350">
      <img width="30" 
           align="absmiddle"
           src="<%= renderRequest.getContextPath()%>/images/question.png" 
           border="0" title="Enable WindDir"/>
                                       
      <input type="checkbox" 
             name="EnableWindDir"
             id="EnableWindDir" /> Wind Direction (&#176;)
      </td>
      <td width="350">
      <img width="30" 
           align="absmiddle"
           src="<%= renderRequest.getContextPath()%>/images/question.png" 
           border="0" title="Enable the Barometric Pressure"/>
                                       
      <input type="checkbox" 
             name="EnableBarPressure"
             id="EnableBarPressure" /> Barometric Pressure (mbar)
      </td>
</tr>    
<tr>
      <td width="350">
      <img width="30" 
           align="absmiddle"
           src="<%= renderRequest.getContextPath()%>/images/question.png" 
           border="0" title="Enable Volumetric Water Content"/>
                                       
      <input type="checkbox" 
             name="EnableVW"
             id="EnableVW" /> Volumetric Water
      </td>
      <td width="350">
      <img width="30" 
           align="absmiddle"
           src="<%= renderRequest.getContextPath()%>/images/question.png" 
           border="0" title="Enable PA_uS"/>
                                       
      <input type="checkbox" 
             name="EnablePA_uS"
             id="EnablePA_uS" /> PA_uS (&#181;s)
      </td>
</tr> 
<tr>
      <td width="350">
      <img width="30" 
           align="absmiddle"
           src="<%= renderRequest.getContextPath()%>/images/info_image.png" 
           border="0" title="Toggle Meteorological Pattern(s)"/>
      
      <input type="checkbox" 
             name="TogglePatterns" 
             id="TogglePatterns" 
             value="checked" 
             onChange="ToggleMeteorologicalPatterns();"/> Toggle Meteorological Pattern(s)
      </td>
</tr>
</table>
    
<br/>          
<p align="center">
<img width="120" src="<%=renderRequest.getContextPath()%>/images/separatore.gif"/>
</p>
             
<br/>
<p>
    C.) Specify some additional settings before to start<br/><br/>
<!--img width="30" 
     align="absmiddle"
     src="<%= renderRequest.getContextPath()%>/images/info_image.png" 
     border="0" title="Date range"/>
     Date range may vary from [ 31/07/2007 to 21/05/2013 ] -->
</p>
<table border="0">    
<tr>
<td width="160">
<img width="30" 
     align="absmiddle"
     src="<%= renderRequest.getContextPath()%>/images/question.png" 
     border="0" title="Please, select the graph type to be used with gnuplot"/>

<label for="trodan_graph">Plot Style</label><em>*</em>
</td>

<td width="400">            
<select name="trodan_graph" 
        style="height:28px;
               width: 255px;
               padding-left: 1px; 
               border-style: solid; 
               border-color: grey; 
               border-width: 1px; 
               padding-left: 1px;">                
           
<option value="lines">lines (Default)</option>        
<option value="points">points</option>        
<option value="linespoints">lines & points</option>
<option value="dots">dots</option>
<option value="impulses">impulses</option>
<option value="histograms">histograms</option>
</select>
</td>                
</tr>
    
<!--tr>
<td width="160">
<img width="30" 
     align="absmiddle"
     src="<%= renderRequest.getContextPath()%>/images/question.png" 
     border="0" title="Select the date Range to be analised " />

<label for="trodan_airtemp">Date <em>*</em></label>
</td>
    
<td width="400">
<label for="trodan_range_from">&nbsp;From </label>
<input type="text"       
       id="trodan_range_from" 
       name="trodan_range_from"       
       style="width: 90px;
              padding-left: 1px; 
              border-style: solid; 
              border-color: grey; 
              border-width: 1px; 
              padding-left: 1px;"/>
           
<label for="trodan_range_to"> To </label>
<input type="text" 
       id="trodan_range_to" 
       name="trodan_range_to"       
       style="width: 90px;
              padding-left: 1px; 
              border-style: solid; 
              border-color: grey; 
              border-width: 1px; 
              padding-left: 1px;"/>       
</td>    
</tr-->

<tr>
<td width="160">
<img width="30" 
     align="absmiddle"
     src="<%= renderRequest.getContextPath()%>/images/question.png" 
     border="0" title="Please, insert here a short description "/>
        
<label for="trodan_desc">Description</label>
</td>
        
<td width="400">
<input type="text" 
       id="trodan_desc"
       name="trodan_desc"
       style="width: 250px;
              padding-left: 1px; 
              border-style: solid; 
              border-color: grey; 
              border-width: 1px; 
              padding-left: 1px;"
       value="Insert here your description"
       size="50" />
</td>           
</tr>
    
<tr><td><br/></td></tr>
   
<tr>
<td width="160">
<img width="30" 
     align="absmiddle"
     src="<%= renderRequest.getContextPath()%>/images/question.png" 
     border="0" title="Enable email notification to the user"/>
                                       
<c:set var="enabled_SMTP" value="<%= SMTP_HOST %>" />
<c:set var="enabled_SENDER" value="<%= SENDER_MAIL %>" />
<c:choose>
<c:when test="${empty enabled_SMTP || empty enabled_SENDER}">        
<input type="checkbox" 
       name="EnableNotification"
       disabled="disabled"
       value="yes" /> Notification        
</c:when>
<c:otherwise>
<input type="checkbox" 
       name="EnableNotification"
       value="yes" /> Notification
</c:otherwise>
</c:choose>
</td>

<td>
<img width="70"
     id="EnableNotificationid"             
     src="<%= renderRequest.getContextPath()%>/images/mailing2.png" 
     border="0"/>
</td>
<td></td>
</tr>
</table>



<table border="0" width="580">
<tr>
    <td width="160">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png" 
         border="0" title="Configure advanced plot options (for experts!)"/>
        
    <a id="aTag" href="javascript:toggleAndChangeText();">
    Advanced plot options &#9660;
    </a>
    
    <div id="divToToggle" display="none">
           
    <!-- ------------------------------- -->
    <div id="divAdvancedOptions" display="none"><br/>    
    <p>
    <img width="30" 
     align="absmiddle"
     src="<%= renderRequest.getContextPath()%>/images/info_image.png" 
     border="0" title="Date range"/>
     Date range may vary from [ 31/07/2007 to 21/05/2013 ]
    </p>
        
    <table id="AdvancedOption_Toggle" border="0">
    <tr>
    <td width="160">
    <img width="30" 
         align="absmiddle"
        src="<%= renderRequest.getContextPath()%>/images/question.png" 
        border="0" title="Select the date Range to be analised " />

    <label for="trodan_airtemp">Date <em>*</em></label>
    </td>
    
    <td width="400">
    <label for="trodan_range_from">&nbsp;From </label>
    <input type="text"       
           id="trodan_range_from" 
           name="trodan_range_from"       
           style="width: 90px;
              padding-left: 1px; 
              border-style: solid; 
              border-color: grey; 
              border-width: 1px; 
              padding-left: 1px;"/>
           
    <label for="trodan_range_to"> To </label>
    <input type="text" 
           id="trodan_range_to" 
           name="trodan_range_to"       
           style="width: 90px;
              padding-left: 1px; 
              border-style: solid; 
              border-color: grey; 
              border-width: 1px; 
              padding-left: 1px;"/>       
    </td>
    </tr>
    
    <tr><td><br/></td></tr>
    
    <tr>
        <td width="160">
        <img width="30" 
         align="absmiddle"
        src="<%= renderRequest.getContextPath()%>/images/question.png" 
        border="0" title="Select the plot options" />
        <label for="trodan_option">Plot Options <em>*</em></label>
        </td>
        <td>
            <input type="radio" name="trodan_option" value="diurnal">Diurnal<br>
            <span style="font-size:1.0em"
                  style="font-family: Tahoma,Verdana,sans-serif,Arial;">
                Each variable is averaged to give hourly output<br/> 
                (24 data point per day)
            </span>
        </td>
    </tr>
    
    <tr><td><br/></td></tr>
    
    <tr>
        <td></td>
        <td>
            <input type="radio" name="trodan_option" value="daily">Daily<br/>
            <span style="font-size:1.0em"
                  style="font-family: Tahoma,Verdana,sans-serif,Arial;">
            All readings for each day averaged to give one value<br/>
            per variable for each calendar day
            </span>
        </td>
    </tr>
    
    <tr><td><br/></td></tr>
    
    <tr>
        <td></td>
        <td>            
            <input type="radio" name="trodan_option" value="monthly">Monthly<br/>
            <span style="font-size:1.0em"
                  style="font-family: Tahoma,Verdana,sans-serif,Arial;">
            Data averaged to give one value per variable for each<br/>
             month of observation data (12 data points per year)
            </span>
        </td>
    </tr>
    
    <tr><td><br/></td></tr>
    
    <tr>
        <td></td>
        <td>            
            <input type="radio" name="trodan_option" 
                   value="nooption" checked="checked">NoOption            
        </td>
    </tr>
    
    </table>
    </div>
    <!-- ------------------------------- -->    
</tr>




    
<tr>                    
<td align="left">
<input type="image" 
       src="<%= renderRequest.getContextPath()%>/images/start-icon.png"
       width="60"                   
       name="submit"
       id ="submit" 
       title="Start the Data Repository Visualization!" />                    
</td>
</tr>
</table>
               
</div>	<!-- Fine Terzo Accordion -->
</div> <!-- Fine Accordions -->
 </fieldset>
</form>                                                                         

<div id="trodan_footer" style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
    <div>TRODAN Data Repository Visualization ~ ver. 1.0.4</div>
    <div>The Italian National Institute of Nuclear Physics (INFN), division of Catania, Italy</div>
    <div>Copyright © 2014 - 2015. All rights reserved</div>
    <div>This work has been partially supported by
        <a href="http://ei4africa.eu/">
            <img width="90" 
                 border="0"
                 src="<%= renderRequest.getContextPath()%>/images/eI4Africa.png" 
                 title="eI4Africa - The eI4Africa Project" />
        </a>
    </div>
</div>
                 
<!--script type="text/javascript">
    var pager = new Pager('results', 13); 
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
</script-->
