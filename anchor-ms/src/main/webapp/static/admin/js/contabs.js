$(function() {
    anchor.request("/permission/menu",null,function(data){
        if(data.code==1){
            tree({'data':data.data});
        }
    });

    function tree(options){
        var DEFAULT_CONFIG={
            menuIcon:'fa-navicon',
            urlIcon:'fa-circle-o'
        };
        var data = options.data;
        var lis = "";
        data.forEach(function(row){
            lis +="<li class='treeview'>"+createTree(row)+"</li>"

        });
        $(".sidebar-menu").append(lis);
        menuEvents();
        function createTree(d){
            var url = d.url||'#';
            var name = d.text;
            var child = d.child;
            var menuIcon = d.icon||DEFAULT_CONFIG[url=='#'?'menuIcon':'urlIcon'];
            var className = url=="#"?"":"T_menuItem";
            var str = "<a href='"+url+"' class='"+className+"'><i class='fa "+menuIcon+"'></i> <span>"+name+"</span>"+(child&&child.length>0?"<i class='fa fa-angle-left pull-right'></i>":"")+" </a>";
            if(child&&child.length>0){
                str +="<ul class='treeview-menu'>";
                child.forEach(function(r){
                    str += "<li>"+createTree(r)+"</li>";
                });
                str +='</ul>';
            }
            return str;
        }
    }
    function f(l) {
        var k = 0;
        $(l).each(function() {
            k += $(this).outerWidth(true)
        });
        return k
    }
    function g(n) {
        var o = f($(n).prevAll()),
        q = f($(n).nextAll());
        var l = f($(".content-tabs").children().not(".T_menuTabs"));
        var k = $(".content-tabs").outerWidth(true) - l;
        var p = 0;
        if ($(".page-tabs-content").outerWidth() < k) {
            p = 0
        } else {
            if (q <= (k - $(n).outerWidth(true) - $(n).next().outerWidth(true))) {
                if ((k - $(n).next().outerWidth(true)) > q) {
                    p = o;
                    var m = n;
                    while ((p - $(m).outerWidth()) > ($(".page-tabs-content").outerWidth() - k)) {
                        p -= $(m).prev().outerWidth();
                        m = $(m).prev()
                    }
                }
            } else {
                if (o > (k - $(n).outerWidth(true) - $(n).prev().outerWidth(true))) {
                    p = o - $(n).prev().outerWidth(true)
                }
            }
        }
        $(".page-tabs-content").animate({
            marginLeft: 0 - p + "px"
        },
        "fast")
    }
    function a() {
        var o = Math.abs(parseInt($(".page-tabs-content").css("margin-left")));
        var l = f($(".content-tabs").children().not(".T_menuTabs"));
        var k = $(".content-tabs").outerWidth(true) - l;
        var p = 0;
        if ($(".page-tabs-content").width() < k) {
            return false
        } else {
            var m = $(".T_menuTab:first");
            var n = 0;
            while ((n + $(m).outerWidth(true)) <= o) {
                n += $(m).outerWidth(true);
                m = $(m).next()
            }
            n = 0;
            if (f($(m).prevAll()) > k) {
                while ((n + $(m).outerWidth(true)) < (k) && m.length > 0) {
                    n += $(m).outerWidth(true);
                    m = $(m).prev()
                }
                p = f($(m).prevAll())
            }
        }
        $(".page-tabs-content").animate({
            marginLeft: 0 - p + "px"
        },
        "fast")
    }
    function b() {
        var o = Math.abs(parseInt($(".page-tabs-content").css("margin-left")));
        var l = f($(".content-tabs").children().not(".T_menuTabs"));
        var k = $(".content-tabs").outerWidth(true) - l;
        var p = 0;
        if ($(".page-tabs-content").width() < k) {
            return false
        } else {
            var m = $(".T_menuTab:first");
            var n = 0;
            while ((n + $(m).outerWidth(true)) <= o) {
                n += $(m).outerWidth(true);
                m = $(m).next()
            }
            n = 0;
            while ((n + $(m).outerWidth(true)) < (k) && m.length > 0) {
                n += $(m).outerWidth(true);
                m = $(m).next()
            }
            p = f($(m).prevAll());
            if (p > 0) {
                $(".page-tabs-content").animate({
                    marginLeft: 0 - p + "px"
                },
                "fast")
            }
        }
    }

    function c() {
        var o = $(this).attr("href"),
        m = $(this).data("index"),
        l = $.trim($(this).text()),
        k = true;
        if (o == undefined || $.trim(o).length == 0) {
            return false
        }
        $(".T_menuTab").each(function() {
            if ($(this).data("id") == o) {
                if (!$(this).hasClass("active")) {
                    $(this).addClass("active").siblings(".T_menuTab").removeClass("active");
                    g(this);
                    $(".iframeContent .T_iframe").each(function() {
                        if ($(this).data("id") == o) {
                            $(this).show().siblings(".T_iframe").hide();
                            return false
                        }
                    })
                }
                k = false;
                return false
            }
        });
        if (k) {
            var p = '<a href="javascript:;" class="active T_menuTab" data-id="' + o + '">' + l + ' <i class="fa fa-times-circle"></i></a>';
            $(".T_menuTab").removeClass("active");
            var n = '<iframe class="T_iframe" name="iframe' + m + '" width="100%" height="100%" src="' + o + '" frameborder="0" data-id="' + o + '" seamless></iframe>';
            $(".iframeContent").find("iframe.T_iframe").hide().parents(".iframeContent");
			$(".iframeContent").append(n);
            $(".T_menuTabs .page-tabs-content").append(p);
            g($(".T_menuTab.active"))
        }
        return false
    }

    function h() {
        var m = $(this).parents(".T_menuTab").data("id");
        var l = $(this).parents(".T_menuTab").width();
        if ($(this).parents(".T_menuTab").hasClass("active")) {
            if ($(this).parents(".T_menuTab").next(".T_menuTab").size()) {
                var k = $(this).parents(".T_menuTab").next(".T_menuTab:eq(0)").data("id");
                $(this).parents(".T_menuTab").next(".T_menuTab:eq(0)").addClass("active");
                $(".iframeContent .T_iframe").each(function() {
                    if ($(this).data("id") == k) {
                        $(this).show().siblings(".T_iframe").hide();
                        return false
                    }
                });
                var n = parseInt($(".page-tabs-content").css("margin-left"));
                if (n < 0) {
                    $(".page-tabs-content").animate({
                        marginLeft: (n + l) + "px"
                    },
                    "fast")
                }
                $(this).parents(".T_menuTab").remove();
                $(".iframeContent .T_iframe").each(function() {
                    if ($(this).data("id") == m) {
                        $(this).remove();
                        return false
                    }
                })
            }
            if ($(this).parents(".T_menuTab").prev(".T_menuTab").size()) {
                var k = $(this).parents(".T_menuTab").prev(".T_menuTab:last").data("id");
                $(this).parents(".T_menuTab").prev(".T_menuTab:last").addClass("active");
                $(".iframeContent .T_iframe").each(function() {
                    if ($(this).data("id") == k) {
                        $(this).show().siblings(".T_iframe").hide();
                        return false
                    }
                });
                $(this).parents(".T_menuTab").remove();
                $(".iframeContent .T_iframe").each(function() {
                    if ($(this).data("id") == m) {
                        $(this).remove();
                        return false
                    }
                })
            }
        } else {
            $(this).parents(".T_menuTab").remove();
            $(".iframeContent .T_iframe").each(function() {
                if ($(this).data("id") == m) {
                    $(this).remove();
                    return false
                }
            });
            g($(".T_menuTab.active"))
        }
        return false
    }

    function i() {
        $(".page-tabs-content").children("[data-id]").not(":first").not(".active").each(function() {
            $('.T_iframe[data-id="' + $(this).data("id") + '"]').remove();
            $(this).remove()
        });
        $(".page-tabs-content").css("margin-left", "0")
    }

    function j() {
        g($(".T_menuTab.active"))
    }

    function e() {
        if (!$(this).hasClass("active")) {
            var k = $(this).data("id");
            $(".iframeContent .T_iframe").each(function() {
                if ($(this).data("id") == k) {
                    $(this).show().siblings(".T_iframe").hide();
                    return false
                }
            });
            $(this).addClass("active").siblings(".T_menuTab").removeClass("active");
            g(this)
        }
    }

    function d() {
        var l = $('.T_iframe[data-id="' + $(this).data("id") + '"]');
        var k = l.attr("src")
    }
    $(".T_menuItem").each(function(k) {
        if (!$(this).attr("data-index")) {
            $(this).attr("data-index", k)
        }
    });
    function menuEvents(){
        $(".T_menuItem").on("click", c);
        $(".T_menuTabs").on("click", ".T_menuTab i", h);
        $(".T_tabCloseOther").on("click", i);
        $(".T_tabShowActive").on("click", j);
        $(".T_menuTabs").on("click", ".T_menuTab", e);
        $(".T_menuTabs").on("dblclick", ".T_menuTab", d);
        $(".T_tabLeft").on("click", a);
        $(".T_tabRight").on("click", b);
        $(".T_tabCloseAll").on("click",
            function() {
                $(".page-tabs-content").children("[data-id]").not(":first").each(function() {
                    $('.T_iframe[data-id="' + $(this).data("id") + '"]').remove();
                    $(this).remove()
                });
                $(".page-tabs-content").children("[data-id]:first").each(function() {
                    $('.T_iframe[data-id="' + $(this).data("id") + '"]').show();
                    $(this).addClass("active")
                });
                $(".page-tabs-content").css("margin-left", "0")
            })
    }

});