<%@ page import="mobi.puut.controllers.WalletUIModel" %>
<%@ page import="mobi.puut.controllers.WalletMainController" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="mobi.puut.controllers.BitcoinWalletController" %>


<%--
  Created by IntelliJ IDEA.
  User: Chaklader
  Date: 5/29/17
  Time: 12:46 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bitcoin Wallet</title>
    <link href="${pageContext.request.contextPath}/static/css/app.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<%
    WalletUIModel model = WalletMainController.getController().getModel();
    boolean canSendMoney = model.isSyncFinished();
    List<String> history = model.getHistory();
    DecimalFormat decimalFormat = new DecimalFormat("#0.0000");
%>


<body class="page_container">
<div class="wallet_page">

    <div class="top_section">

        <div class="balance_box">
            <div class="balance_row">
                <div class="fild_label">Balance</div>
                <div class="fild_value">
                    <%--<%= decimalFormat.format(model.getBalance().getValue()) %>&nbsp; SATOSHI--%>
                    <%= decimalFormat.format(model.getBalanceFloatFormat()) %>&nbsp; BTC
                </div>
            </div>
            </br>
            <div class="address_row">
                <%= model.isSyncFinished() ? model.getAddress().toString() : "Getting the address ..."%>
            </div>
        </div>

        <div class="buttons_box">
            <button type="button" class="btn btn-default btn-lg active" <%= canSendMoney ? "" : "disabled='true'"%>
                    data-toggle="modal" data-target="#myModal">Send money
            </button>
        </div>
    </div>

    <!-- history_section -->
    <div class="history_section">
        <% if (!model.isSyncFinished() && !history.isEmpty()) {%>
        Getting the transaction info ...
        <% } else if (model.isSyncFinished() && history.isEmpty()) {%>
        No transaction recorded yet!!
        <% } else {
            for (String transaction : history) {
        %>
        <p><%= transaction%>
        </p>
        <% }
        }%>
    </div>

    <div class="status_line">
        <%=  model.isSyncFinished() ? "Synchronized" : "Synchronizing to the block chain ..."%>
    </div>
</div>


<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <%--modal contents here--%>
        <div class="modal-content">

            <div class="model-header">
                <button type="button" class="close" data-dismiss="modal">&times!</button>
                <h4 class="modal-title">Send Money</h4>
            </div>

            <%--<form id="amount-form" class="form-horizontal" action="sendMoney.jsp" method="POST">--%>
            <form id="amount-form" class="form-horizontal" action="/sendMoney" method="POST">

                <div class="modal-body">

                    <div class="form-group">
                        <label for="amount" class="col-sm-2 control-label">Send</label>
                        <div class="col-xs-4">
                            <input id="amount" name="amount" class="form-control" value="0">
                        </div>
                        <div class="btc-col">
                            <span>BTC</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="address" class="col-sm-2 control-label">to</label>
                        <div class="col-sm-10">
                            <input id="address" name="address" class="form-control">
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-default">Send</button>
                </div>

            </form>
        </div>
    </div>
</div>
</body>

<script>
    <% if(!model.isSyncFinished()) {%>
    setTimeout(function () {
        window.location.reload(1);
    }, 3000);
    <% }%>
</script>
</html>
