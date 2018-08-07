<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Vote history</title>
    <%@include file="_css-js.jsp" %>
</head>
<body>
    <div>
        <!-- Menu bar -->
        <%@include file="_menu-bar1.jsp" %>
        <%@include file="_vote-nav.jsp" %>
        <H2>History of your votes</H2>
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-4">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-striped" id="historyTable">
                        <thead>
                            <tr>
                                <th width="20%">Date</th>
                                <th width="20%">Rate (%)</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $(document).ready( function () {
            $('#historyTable').DataTable({
            	"ajax": 'load-history-votes',
                "columns" : [
                    {"data" : "voted", render: function(d) {
                                                return moment(d).format("YYYY-MM-DD HH:mm:ss");
                                               }
                    },
                    {"data" : "voteValue"}
                 ]
            });
        });
    </script>
</body>
</html>