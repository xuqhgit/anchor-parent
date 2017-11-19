// $(function () {
//     window.Modal = function () {
//         var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
//         var alr = $("#com-alert");
//         var ahtml = alr.html();
//
//         var _tip = function (options, sec) {
//             alr.html(ahtml);    // 复原
//             alr.find('.ok').hide();
//             alr.find('.cancel').hide();
//             alr.find('.modal-content').width(500);
//             _dialog(options, sec);
//
//             return {
//                 on: function (callback) {
//                 }
//             };
//         };
//
//         var _alert = function (options) {
//             alr.html(ahtml);  // 复原
//             alr.find('.ok').removeClass('btn-success').addClass('btn-primary');
//             alr.find('.cancel').hide();
//             _dialog(options);
//
//             return {
//                 on: function (callback) {
//                     if (callback && callback instanceof Function) {
//                         alr.find('.ok').click(function () { callback(true) });
//                     }
//                 }
//             };
//         };
//
//         var _confirm = function (options) {
//             alr.html(ahtml); // 复原
//             alr.find('.ok').removeClass('btn-primary').addClass('btn-success');
//             alr.find('.cancel').show();
//             _dialog(options);
//
//             return {
//                 on: function (callback) {
//                     if (callback && callback instanceof Function) {
//                         alr.find('.ok').click(function () { callback(true) });
//                         alr.find('.cancel').click(function () { return; });
//                     }
//                 }
//             };
//         };
//
//         var _dialog = function (options) {
//             var ops = {
//                 msg: "提示内容",
//                 title: "操作提示",
//                 btnok: "确定",
//                 btncl: "取消"
//             };
//
//             $.extend(ops, options);
//
//             var html = alr.html().replace(reg, function (node, key) {
//                 return {
//                     Title: ops.title,
//                     Message: ops.msg,
//                     BtnOk: ops.btnok,
//                     BtnCancel: ops.btncl
//                 }[key];
//             });
//
//             alr.html(html);
//             alr.modal({
//                 width: 250,
//                 backdrop: 'static'
//             });
//         }
//
//         return {
//             tip: _tip,
//             alert: _alert,
//             confirm: _confirm
//         }
//
//     }();
// });
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
var anchor = {};
var _dictData;
function loadDictData(){
    if(!_dictData){
        if(self.location!=top.location){
            return window.parent.loadDictData();
        }
        else{
            anchor.request("/dict/dictTree",{},function(data){
                _dictData=data.data;
            },{async:false})
        }
    }
    return _dictData;
}
$(function() {
    var DEFAULT_TYPE = 'blue';
    var DEFAULT_BOOTSTRAP_TABLE_SETTING = {
        method:"post",
        contentType : "application/x-www-form-urlencoded",
        striped: true,
        toolbar: '#toolbar',
        pageList: [13, 25, 50, 100],
        height:"650",
        sidePagination: "server",
        clickToSelect: true,   //是否启用点击选中行
        sortOrder: "asc",     //排序方式
        pageNumber:1,      //初始化加载第一页，默认第一页
        pageSize: 13,      //每页的记录行数（*）
        pagination:true
    };

    anchor.defaultSettings={async:true};
    anchor.request = function(url,param,func,settings){
        var defaultSettings = {
            filter:false,
            async:true
        };
        var _settings = $.extend({},defaultSettings,settings);
        $.ajax({
            url: url,
            data: param,
            dataType: "json",
            type: "POST",
            traditional: true,
            async: _settings.async,
            success: function (data) {
                if(data['code']==300){
                    anchor.alert(data['message']);
                    parent.location.reload();
                    return;
                }
                if(_settings['filter']&&data['code']!=1&&data['message']){
                    anchor.alert(data['message']);
                }
                if(func)func(data);
            }
        });

    };
    anchor.getDict=function(dictCode){
        var dictData = loadDictData();
        if(!dictData)return;
        for(var i=0;i<dictData.length;i++){
            if(dictData[i].code==dictCode){
                return dictData[i];
            }
        }
    };

    anchor.getDictItemTextByValue=function(dictItems,value){
        for(var i=0;i<dictItems.length;i++){
            if(dictItems[i].value==value){
                return dictItems[i].text;
            }
            if(dictItems[i].child&&dictItems[i].child.length>0){
                var text = anchor.getDictItemTextByValue(dictItems[i].child,value);
                if(text){
                    return text;
                }
            }
        }
    };
    anchor.createFromGroupSelect=function(id,dict,selectValue,defaultShow){
        var options="";
        if(defaultShow){
            options+="<option value='"+defaultShow[0]+"'>"+defaultShow[1]+"</option>"
        }
        selectValue = selectValue?selectValue:$('#'+id).attr("data-value");
        dict.list.forEach(function(val){
            options+="<option value='"+val.value+"' "+(selectValue&&selectValue==val.value?"selected":"")+">"+val.text+"</option>"
        });
        $('#'+id).html(options);
    };
    anchor.bootstrapTable=function(id,setting){
        var $setting = $.extend({},DEFAULT_BOOTSTRAP_TABLE_SETTING,setting);
        return $('#'+id).bootstrapTable($setting);
    };
    anchor.alert=function(msg,title){
        $.alert({
            title: "提示"|title,
            content: msg,
            type: DEFAULT_TYPE,
            buttons:{
                ok: {
                    text:'确定'
                }
            }

        });
    };
    anchor.confirm=function(msg,callback){
        $.confirm({
            title: '提示!',
            content: msg,
            type: DEFAULT_TYPE,
            buttons: {
                confirm: {
                    text:"确认",
                    btnClass: 'btn-blue',
                    action:function () {
                        if (callback)callback();
                    }
                }
                ,
                cancel: {
                    text:"取消",
                    action:function () {
                    }
                }
            }
        });
    }
    anchor.dialog = function (options1,option2) {
        $.dialog(options1,option2)
    };

    anchor.validate= function(options,callback) {
        var default_options ={
            errorElement: 'span',
            errorClass: 'help-block',
            focusInvalid: false,
            highlight: function (element) {
                $(element).closest('.form-group').addClass('has-error');
            },
            success: function (label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function (error, element) {
                element.parent('div').append(error);
            },
            submitHandler: function (form) {
//                    form.submit();
                if(callback)callback();
            }
        };

      return  $('#'+options.id).validate($.extend(default_options,options));

    };
    /**
     * 用于生成需要校验字段的配置
     * @param config
     * @param validFields
     */
    anchor.validFieldConfig=function(config,validFields){
        var cfg = {rules:{},messages:{}};
        validFields.forEach(function(e){
            if(config[e]){
                cfg.rules[e]=config[e].rule;
                cfg.messages[e]=config[e].message;
            }
        });
        return cfg;
    };
    /**
     * 用于生成需要校验字段的配置
     * @param config
     * @param validFields
     */
    anchor.formField=function(formId){
        var formJson = $('#'+formId).serializeObject();
        var arr = [];
        for(var i in formJson){
            arr.push(i);
        }
        return arr;
    }
});
