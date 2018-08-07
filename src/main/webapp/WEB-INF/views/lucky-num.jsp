<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="r" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>OwnBank</title>
<%@include file="_css-js.jsp" %>
<style>
.image-option {
    border: 4px solid transparent;
    padding: 2px;
}

.image-option:hover { 
    border: 4px solid yellow;
    padding: 2px;
}

.image-option:clicked { 
    border: 4px solid yellow;
    padding: 2px;
}

.big-num { 
    color: blue;
    font-size: 100px;
}

</style>

</head>
<body>
    <!-- Menu bar -->
    <%@include file="_menu-bar2.jsp" %>
    <jsp:include page="_lucky-number-nav.jsp"></jsp:include>
<div>
    <div class="jumbotron">
    <H3>
    Click on the <b>hexagon</b> to select your randomized number.
    <br/>
    </H3>
    </div>
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
             <div class="jumbotron">
                <div id="container">
                    <div id="game" class="canvas responsive-canvas"></div>
                </div>
             </div>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <span id="status" class="center"></span>
        </div>
    </div>

</div>

<script>
    /** isSaved: flag of saving lucky number.*/
    var isSaved = false;

    var gameConfig = {
        canvasId: 'game',
        canvasWidth: $("#container").width(),
        canvasHeight: 300
    };
    
    /**
     * Init casvas for graph.
     * @param config: contains configuration information for graph
     *  
     *  title:
     *  canvasId: 
     *  canvasWidth: 
     *  canvasHeight:
     *  minX: 
     *  minY: 
     *  maxX: 
     *  maxY: 
     *  unitsPerTickX:
     *  unitsPerTickY:
     *  @return Stage of the graph
     */
    function Graph(config) {
        // first we need to create a stage
        var stage = new Konva.Stage({
            container: config.canvasId, // id of container <div>
            width: config.canvasWidth,
            height: config.canvasHeight
        });
        console.log("Game config=" + JSON.stringify(config));
        // then create layer
        var layer = new Konva.Layer();
  
        return stage;
    }
    
    var stage = new Graph(gameConfig);

    var layer = new Konva.Layer();

    var num = new Konva.Text({
        x: stage.getWidth() / 2,
        y: stage.getHeight() / 2 - 40,
        text: '1234567',
        fontSize: 60,
        fontFamily: 'Calibri',
        fill: 'red'
      });

    var bgImage = new Image();
    bgImage.src = "resources/image/ThachLN.png";
    
    var hexagon = new Konva.RegularPolygon({
        x: stage.getWidth() / 2,
        y: stage.getHeight() / 2,
        sides: 6,
        radius: 150,
        fillPatternImage: bgImage,
        stroke: 'gray',
        
        strokeWidth: 4
    });


    
    layer.add(hexagon);
    layer.add(num);
    stage.add(layer);

    var amplitude = 100;
    var period = 2000;
    // in ms
    var centerX = stage.getWidth() / 2;

    var anim = new Konva.Animation(function(frame) {
        hexagon.setX(amplitude * Math.sin(frame.time * 2 * Math.PI / period) + centerX);
        num.setX(amplitude * Math.sin(frame.time * 2 * Math.PI / period) + centerX - num.width() / 2);
        var genNum = Math.trunc(Math.random() * 1000000);
        num.setText(genNum);
    }, layer);

    anim.start();

    hexagon.on('mouseup', function () {
        anim.stop();
        submitSelectedNum(num.getText());
    });
    num.on('mouseup', function () {
        anim.stop();
        submitSelectedNum(num.getText());
    });

    function submitSelectedNum(luckyNum) {

        if (isSaved) {
            console.log("The lucky number is saved.");
            return;
        }

        var data = {};
        data["luckyNum"] = luckyNum;

        $.ajax({
            type: 'POST',
            url: 'save-luckynum',
            data: data,
    
            success : function(result) {
              // Debug on console
              console.log("message=" + result.status);
              $('#status').html("<H3>Your lucky number is <b>" + luckyNum + "</b></H3>");
            },
            error : function(result) {
              $('#status').html("<div class='alert alert-danger'>" +
            		    "Could not connect to server!" + 
            		    "</div>");
          	  console.log("message=" + result);
            }
        });

        // turn on the flag
        isSaved = true;
    }
</script>

</body>
</html>
