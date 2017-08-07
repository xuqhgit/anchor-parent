/**
 * Created by Administrator on 2017/8/3.
 */
var s = undefined;
$(function () {
    var allCode = {"1": "all", "2": "half"};
    var standard="1",letScore="2",bigSmall="3";
    function start() {
        if ("滚球" != $("#tit_content").text()) {
            return;
        }
        var spans = $("#today_body tr .league-name span");
        for (var i = 0; i < spans.length; i++) {
            var $span = $(spans[i]);
            var spanId = $span.attr("id");
            var leagueName = $span.text();
            var matchId = spanId.split("_")[1];
            var leagueId = spanId.split("_")[2];
            //wind-lm-1284626 wind-tr windtr-match_1284626_children1
            matchData(matchId, leagueId, leagueName);
        }
        return 1;

    }

    function getAllMatchHb() {
        var allMatch = localStorage.getItem("allMatch");
        if (!allMatch) {
            return {};
        }
        return JSON.parse(allMatch);
    }

    function saveAllMatchHb(allMatch) {
        localStorage.setItem("allMatch", JSON.stringify(allMatch));
    }

    function confirm(text) {
        return true;
    }

    function saveMatchOrder(order) {
        localStorage.setItem("order", order);
    }

    function getOrderKey(isHalf, homeScore, guestScore, playType, homeOrGuest, point) {
        return isHalf + "_" + homeScore + "_" + guestScore + "_" + playType + "_" + homeOrGuest + "_" + point;
    }

    function getMatchOrder() {
        var allOrder = localStorage.getItem("order");
        if (!allOrder) {
            return {};
        }
        return JSON.parse(allOrder);
    }

    function saveMatchInfo(matchData) {
        var data = {};
        var allMatch = getAllMatchHb();
        if (!allMatch[matchData["id"]]) {
            data["all"] = matchData["all"];
            data["half"] = matchData["half"];
            data["homeName"] = matchData["homeName"];
            data["guestName"] = matchData["guestName"];
            data["homeRed"] = matchData["homeRed"];
            data["guestRed"] = matchData["guestRed"];
            data["guestRedTime"] = "";
            data["homeRedTime"] = "";
            data["leagueId"] = matchData["leagueId"];
            data["leagueName"] = matchData["leagueName"];
            data["homeRank"] = 0;
            data["guestRank"] = 0;
            data["time"] = new Date().getTime();
            data["score"] = [[0, 0, "0"]];
            data["id"] = matchData["id"];
            allMatch[matchData["id"]] = data;
        }
        else {
            data = allMatch[matchData["id"]];
        }
        var logStr = data["homeName"] + " vs " + data["guestName"];
        var change = false;
        if (data["homeRed"] != matchData["homeRed"]) {
            data["homeRedTime"] = matchData["minute"];
            data["homeRed"] = matchData["homeRed"];
            if (matchData["homeRed"] == 1) {
                console.info(logStr + " 主队红牌 " + matchData["minute"]);
            }
            change = true;
        }
        if (data["guestRed"] != matchData["guestRed"]) {
            data["guestRedTime"] = matchData["minute"];
            data["guestRed"] = matchData["guestRed"];
            if (matchData["guestRed"] == 1) {
                console.info(logStr + " 客队红牌 " + matchData["minute"]);
            }
            change = true;
        }

        var score = data["score"][data["score"].length - 1];
        if (score[0] != matchData["homeScore"] || score[1] != matchData["guestScore"]) {
            data["score"].push([matchData["homeScore"], matchData["guestScore"], matchData["minute"]]);
            var printStr = "";
            for (var i = 1; i < data["score"].length; i++) {
                printStr += "   " + data["score"][i][0] + ":" + data["score"][i][1] + " 时间：" + data["score"][i][2];
            }
            console.info(logStr + " 比分数据更新：" + printStr);
            change = true;
        }
        if (change) {
            saveAllMatchHb(allMatch);
        }
        //下单
        order(matchData, data);
    }

    //获取某一场比赛 赔率数据
    function matchData(matchId, leagueId, leagueName) {
        var trs = $(".wind-lm-" + matchId);
        if (trs.length == 0) {
            console.error("未获取到赛事[" + matchId + "] 数据");
            return;
        }
        var homeName = "";
        var guestName = "";
        var matchData = {
            "leagueId": leagueId,
            "leagueName": leagueName,
            "guestName": "",
            "homeName": "",
            "minute": "",
            "homeScore": 0,
            "guestScore": 0,
            "homeRed": 0,
            "guestRed": 0,
            "all": {},
            "half": {},
            "id": matchId
        };

        for (var i = 0; i < trs.length; i++) {
            var tds = $(trs[i]).children();
            for (var j = 0; j < tds.length; j++) {
                var td = $(tds[j]);
                var tdId = td.attr("gtid");
                if (td.hasClass("t-name")) {
                    var name = td.find("a").text();
                    var red = 0;
                    if (td.find("a em.red-style") && "1" == td.find("a em.red-style").text()) {
                        red = 1;
                    }
                    if (!matchData["homeName"]) {
                        matchData["homeName"] = name;
                        matchData["homeRed"] = red;

                    } else if (!matchData["guestName"]) {
                        matchData["guestName"] = name;
                        matchData["guestRed"] = red;
                    }
                }
                else if (td.hasClass("t-time") && !matchData["minute"]) {
                    var time = td.find(".sc-span").text();
                    matchData["minute"] = time.replace("'", "");
                    var score = td.find(".sc-exp").children();
                    matchData["homeScore"] = $(score[0]).text();
                    matchData["guestScore"] = $(score[1]).text();
                }
                else if (tdId && tdId != "gt") {
                    var span = td.children();
                    var sid = span.attr("sid").split("_");
                    var playType = sid[1];
                    var main = sid[2];
                    var all = sid[3];
                    var homeGuest = sid[4];
                    if (!matchData[allCode[all]][playType]) {
                        matchData[allCode[all]][playType] = []
                    }
                    var playTypeArray = matchData[allCode[all]][playType];
                    if (playTypeArray.length < (main + 1)) {
                        playTypeArray.push({});
                    }
                    var data = playTypeArray[main];
                    data[homeGuest + "_id"] = span.find(".w-odds ").attr("trid");
                    data[homeGuest] = span.find(".odds").text();
                    var point = span.find(".point");
                    if (point && point.text()) {
                        if (homeGuest == '0') {
                            data["point"] = handicapChange(point.text());
                        }
                        else {
                            data["point"] = -handicapChange(point.text());
                        }
                    }
                }
            }
        }
        //保存赛事信息
        saveMatchInfo(matchData);
    }

    function handicapChange(handicap) {
        if (handicap && handicap.indexOf("/") > 0) {
            return Number(handicap.split("/")[0]) + 0.25;
        }
        return handicap;
    }


    //first 1 标准盘 2 让分盘 3 大小球 4 单双
    //second 0 1 2 代表盘口 0 主盘
    //third 1 全 2 半
    //froth 0 主 1 客

    var startFlag = 0;
    s = setInterval(function () {
        if (startFlag == 0) {
            startFlag = 1;
            start();
            startFlag = 0;
        }
        else {
            console.info("上一个任务未执行完...");
        }
    }, 3000);
    var submitMoney = 2;

    function order(matchData, matchInfo) {
        if ($.isEmptyObject(matchData["all"]) && $.isEmptyObject(matchData['half'])) {
            return;
        }
        if(matchData['minute']=='-'){
            return ;
        }
        //红牌下注
        redBet(matchData, matchInfo);
        //比分下注
        scoreBet(matchData, matchInfo);



        //半场下单
        //if (!$.isEmptyObject(matchData["half"])) {
        //    var data = matchData["half"];
        //    var isHalf = "half";
        //    for (var key in data) {
        //        switch (key) {
        //            case "1":
        //                //标准盘 查看赔率最小的
        //                standard(data, matchInfo, isHalf);
        //                break;
        //            case "2":
        //                //让分盘
        //                letScore(data, matchInfo, isHalf);
        //                break;
        //            case "3":
        //                //大小盘
        //                bigSmall(data, matchInfo, isHalf);
        //                break;
        //            default:
        //                continue;
        //        }
        //    }
        //}
        //全场下单
        //if (!$.isEmptyObject(matchData["all"])) {
        //    var isHalf = "all";
        //    var data = matchData["half"];
        //    for (var key in data) {
        //        switch (key) {
        //            case "1":
        //                standard(data, matchInfo, isHalf);
        //                //标准盘
        //                break;
        //            case "2":
        //                //让分盘
        //                letScore(data, matchInfo, isHalf);
        //                break;
        //            case "3":
        //                //大小盘
        //                bigSmall(data, matchInfo, isHalf);
        //                break;
        //            default:
        //                continue;
        //        }
        //    }
        //}
    }

    /**
     * 提交订单
     *
     * @param trid 赔率元素ID
     * @param orderMoney 下单金额
     * @param point 盘口
     * @param orderKey 订单KEY
     * @returns {boolean}
     */
    function submitOrder(trid, orderMoney, point, orderKey) {
        $("#t_bet").val(orderMoney);
        $("span[trid=" + trid + "]").click();
        if ($("#t_ratio")) {
            var curOdds = Number($("#t_ratio span").text());
            var money = $("#userGive").text().replace("CNY ", "");
            if (money > 2) {
                var matchOrderInfo = {
                    "odds": curOdds,
                    "money": orderMoney,
                    "point": point,
                    "statue": 0,
                    "message": "",
                    "orderCode": "",
                    "time": new Date().getTime()
                };

                $("#confirm_bet").click();

                return true;
            }
            else {
                console.error("余额不足无法下单");
            }
        }
        else {
            console.error("无法交易:" + $(".warn-icon").text());
        }
        return false;
    }


    /**
     * 标准盘下单预判 返回下单金额 作废
     *
     * @param curPointData 当前盘口数据
     * @param matchInfo 赛事信息
     * @param isHalf 是否半场

     */
    function standard(curPointData, matchInfo, isHalf) {
        var point = "";
        var orderKey = getOrderKey(isHalf, homeScore, guestScore, "1", homeOrGuest, point);
        var trid = "";
      //  submitOrder(trid, submitMoney, point, orderKey);

    }


    /**
     * 让分盘下单预判 返回下单金额 作废
     *
     * @param curPointData 当前盘口数据
     * @param matchInfo 赛事信息
     * @param isHalf 是否半场
     */
    function letScore(curPointData, matchInfo, isHalf) {
        var point = "";
        var trid = "";
        var orderKey = getOrderKey(isHalf, homeScore, guestScore, "1", homeOrGuest, point);
       // submitOrder(trid, submitMoney, point, orderKey);

    }


    /**
     *大小盘下单预判 返回下单金额 作废
     *
     * @param curPointData 当前盘口数据
     * @param matchInfo 赛事信息
     * @param isHalf 是否半场
     */
    function bigSmall(curPointData, matchInfo, isHalf) {
        var point = "";
        var trid = "";

        if (isHalf == "all") {

        }
        else {
            //半场下注
            var bsData = curPointData[bigSmall];


        }
        var orderKey = getOrderKey(isHalf, homeScore, guestScore, bigSmall, homeOrGuest, point);
        submitOrder(trid, submitMoney, point, orderKey);

    }
    //根据时间推移 进球困难系数
    var timeScore=[[0,5,9],[6,10,8],[11,15,7],[16,20,6],[21,25,5],[26,30,4],[31,35,3],[36,40,2],[41,50,1]];
    //进球数 难易程度 体现出 进球越多 就比赛越容易进去 但是 对于
    var scorePro = [[1,1],[2,3],[3,6],[4,10],[5,20],[6,30],[7,50],[8,100]];
    //21 为 一方吃两个红牌 3 为 一方 一个 一方两个 2 为 一边一个 4 一边两个 1其中一个吃一个
    var redPro = [[1,50],[2,25],[3,75],[21,100],[4,50]];
    function redBet(matchData,matchInfo){
        if(matchInfo["homeRed"]==0&&matchInfo["guestRed"]==0){
            return;
        }
        var startMinute = matchData['minute'].split(" ")[1];
        if(matchData['minute'].indexOf("1H")>0){

        }
        else if(matchData['minute'].indexOf("2H")>0){

        }


        if(redTime.indexOf("1H")>-1){

        }
        else if(redTime.indexOf("2H")>-1){

        }
        if(matchInfo["homeRed"]>matchInfo["guestRed"]){
            var redTime = matchInfo["homeRedTime"];



        }
        else if(matchInfo["homeRed"]<matchInfo["guestRed"]){

        }

    }
    function scoreBet(matchData,matchInfo){

    }

});







