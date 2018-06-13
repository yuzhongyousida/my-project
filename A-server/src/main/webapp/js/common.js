jQuery(function($) {
  //override dialog's title function to allow for HTML titles
  $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
    _title: function (title) {
      var $title = this.options.title || '&nbsp;'
      if (("title_html" in this.options) && this.options.title_html == true)
        title.html($title);
      else title.text($title);
    }
  }));
});
function notifyError(message) {
  $.notify({
    icon: 'glyphicon glyphicon-warning-sign',
    message: "Error: " + message
  }, {
    type: 'danger',
    offset: 73,
    placement: {
      from: "top",
      align: "center"
    },
    delay: 3000
  });
}

function notifySuccess(message) {
  $.notify({
    icon: 'glyphicon glyphicon-warning-sign',
    message: "success: " + message
  }, {
    type: 'success',
    offset: 73,
    placement: {
      from: "top",
      align: "center"
    },
    delay: 2000
  });
}

Date.prototype.pattern = function (fmt) {
  var o = {
    "M+": this.getMonth() + 1, //月份
    "d+": this.getDate(), //日
    "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
    "H+": this.getHours(), //小时
    "m+": this.getMinutes(), //分
    "s+": this.getSeconds(), //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    "S": this.getMilliseconds() //毫秒
  };
  var week = {
    "0": "/u65e5",
    "1": "/u4e00",
    "2": "/u4e8c",
    "3": "/u4e09",
    "4": "/u56db",
    "5": "/u4e94",
    "6": "/u516d"
  };
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  }
  if (/(E+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
  }
  for (var k in o) {
    if (new RegExp("(" + k + ")").test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    }
  }
  return fmt;
};

/**
 * @func
 * @desc 激活侧边栏
 * @param openList [string] - 需要展开的列表
 * @param activeList [string] - 需要设为active的列表
 */
function activeSidebar(openList, activeList) {
    $.each($("#sidebar li"), function (index, item) {
        if (isListContainStr(openList, $(item).attr("id"))) {
            $(item).addClass("open");
        }
        if (isListContainStr(activeList, $(item).attr("id"))) {
            $(item).addClass("active");
        }
    })
}

function isListContainStr(inputList, search) {
  var found = false;
  $.each(inputList, function (i, cell) {
    if (search === cell) {
      found = true;
      return false;  // break each
    }
  });
  return found;
}

function refresh() {
  window.location.reload();
}

//是否是正整数
function isPositiveInt(val) {
  var result = true;
  var r = /^[1-9]\d*$/;
  if(!r.test(val) || !r.test(val.trim())) {
    result = false;
  }
  return result;
}

//是否是正数
function isPositiveDigit(val) {
  var result = true;
  var r = /^\d+(\.\d+)?$/;
  if(!r.test(val) || !r.test($.trim(val))) {
    result = false;
  }
  return result;
}

//是否是[0,1]之间的数
function isZeroToOneDigit(val) {
  var result = true;
  var r = /^[0-1]$|^0\.[0-9]+$/;
  if(!r.test(val) || !r.test(val.trim())) {
    result = false;
  }
  return result;
}

function drawPieChart($container, title, json) {
  var data = [];
  for (var i = 0; i < json.length; ++i) {
    var item = [];
    item.push(json[i].key);
    item.push(json[i].count);
    data.push(item);
  }
  $container.highcharts({
    credits: {
      enabled: false
    },
    chart: {
      plotBackgroundColor: null,
      plotBorderWidth: null,
      plotShadow: false,
      type: 'pie'
    },
    legend: {
      enabled: true,
      layout: 'vertical',
      align: 'right',
      verticalAlign: 'middle',
      itemStyle: {
        fontSize: 12,
        color: '#818284'
      },
      symbolWidth: 10,
      useHTML: true,
      labelFormatter: function () {
        return this.name + ' ' + this.y;
      }
    },
    title: {
      text: title,
      align: 'center',
      verticalAlign: 'middle',
      x: -60,
      y: 10
    },
    tooltip: {
      pointFormat: '<b>{point.percentage:.1f}%</b>'
    },
    plotOptions: {
      pie: {
        allowPointSelect: true,
        cursor: 'pointer',
        dataLabels: {
          enabled: false
        },
        showInLegend: true
      }
    },
    series: [{
      type: 'pie',
      innerSize: '50%',
      data: data
    }]
  });
}

function drawTrendChart($container, series, showDataLables, style) {
  var options = getStockChartOptions();
  var settings = $.extend(true, {}, options, {
    chart: {
      zoomType: 'y'
    },
    legend: {
      verticalAlign: 'top',
      labelFormatter: function () {
        return this.name;
      }
    },
    tooltip: {
      formatter:function(){
        var htm = "";
        var timeStr = '';
        var date = new Date(this.x);
        var year = date.getFullYear();
        timeStr += year;
        if(style.indexOf('%m') != -1){
          var month = date.getMonth() + 1;
          timeStr += ('-' + month);
        }
        if(style.indexOf('%d') != -1){
          var day = date.getDate();
          timeStr += ('-' + day);
        }

        htm += '<span style="font-size: 10px">' + timeStr +'</span><br/>';
        var total = 0;
        var points = this.points;
        for(var i = 0;i < points.length ; i++){
          var point = points[i];
          htm += '<span style="color:'+ point.color + '">\u25CF </span>' + point.series.name + ': <b>' + point.y + '</b><br/>';
          total += point.y
        }
        htm += '<span style="color:#000000">\u25CF</span> 总计: <b>' + total + '</b><br/>';
        return htm;
      }
    },
    xAxis: {
      type: 'datetime',
      labels: {
        formatter :function() {
          return Highcharts.dateFormat(style, this.value);
        }
      }
    },
    series: series,
    scrollbar:{
      enabled:true,
      showFull:false
    },
    plotOptions: {
      series: {
        dataLabels: {
          enabled: showDataLables
        }
      }
    }
  });
  $container.highcharts('StockChart', settings);
}

function drawStockChartWithTitle($container, title, series, valueSuffix) {
  var options = getStockChartOptions();
  var settings = $.extend(true, {}, options, {
    title: {
      text:  title ,
      style: {
        "fontSize": "14px",
        fontWeight: 'bold',
        "color": "#818284"
      },
      align: 'center',
      y: 10
    },
    legend: {
      verticalAlign: 'top',
      y:25
    },
    tooltip: {
      valueSuffix: valueSuffix
    },
    series: series
  });
  $container.highcharts('StockChart', settings);
}

function datetimePickerToggle($pickerInput) {
  $pickerInput.unbind('focus')
      .on('click', function () {
        $(this).datetimepicker('toggle');
      }).on('blur', function () {
    $(this).datetimepicker('toggleHide');
  });
}


function displayNumberFriendly(number) {
  return number.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
}


function isValidIP(ip) {
    var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
    return reg.test(ip);
}

/* 格式化时间，将时间戳转化为时间  yyyy-MM-dd HH-mm-ss
 */
function formatDateTime(inputTime) {
    if(inputTime === null) {
      return "";
    }
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
}

// 验证是否为正整数
// obj：数字 posOrNeg：1正整数 2：负整数 0：不要求
function checkInteger(obj, posOrNeg, startWhole, endWhole) {
    var posNeg;
    if (posOrNeg == 1 || posOrNeg == '1') {
        posNeg = /^[+]?]*$/;
    } else if (posOrNeg == 2 || posOrNeg == '2') {
        posNeg = /^[-]?]*$/;
    } else {
        posNeg = /^[+-]?]*$/;
    }
    if (!checkIntegerUtil(obj.value, posOrNeg, startWhole, endWhole)
        && !posNeg.test(obj.value)) {
        obj.value = "";
        obj.focus();
        return false;
    }
}

// 验证是否为正整数 可是为0
// obj：数字 posOrNeg：1正整数 2：负整数 0：不要求
function checkIntegerHasZero(obj, posOrNeg, startWhole, endWhole) {
    var posNeg;
    if (posOrNeg == 1 || posOrNeg == '1') {
        posNeg = /^[+]?]*$/;
    } else if (posOrNeg == 2 || posOrNeg == '2') {
        posNeg = /^[-]?]*$/;
    } else {
        posNeg = /^[+-]?]*$/;
    }
    if (!checkIntegerUtil(obj.value, posOrNeg, startWhole, endWhole)
        && !posNeg.test(obj.value) && obj.value != 0) {
        obj.value = "";
        obj.focus();
        return false;
    }
}

// 是否是小数
// obj dec小数位
function checkDecimal(obj, posOrNeg, startWhole, endWhole, startDec, endDec) {
    var re;
    var posNeg;
    if (posOrNeg == 1 || posOrNeg == '1') {
        re = new RegExp("^[+]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
            + startDec + "," + endDec + "})?$");
        posNeg = /^[+]?]*$/;
    } else if (posOrNeg == 2 || posOrNeg == '2') {
        re = new RegExp("^[-]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
            + startDec + "," + endDec + "})?$");
        posNeg = /^[-]?]*$/;
    } else {
        re = new RegExp("^[+-]?\\d{" + startWhole + "," + endWhole
            + "}(\\.\\d{" + startDec + "," + endDec + "})?$");
        posNeg = /^[+-]?]*$/;
    }
    if (!re.test(obj.value) && !posNeg.test(obj.value)) {
        obj.value = "";
        obj.focus();
        return false;
    }
}


// 验证一个数字是否为整数（正、负、正负）
// 整数的长度
function checkIntegerUtil(value, posOrNeg, startWhole, endWhole) {
    var re;
    if (parseInt(startWhole) > 0) {
        startWhole = parseInt(startWhole) - 1;
    } else {
        startWhole = 1;
    }
    if (parseInt(endWhole) > 0) {
        endWhole = parseInt(endWhole) - 1;
    } else {
        endWhole = 1;
    }
    if (posOrNeg == 1 || posOrNeg == '1') {
        re = new RegExp("^[+]?[0-9]\\d{" + startWhole + "," + endWhole + "}$");
    } else if (posOrNeg == 2 || posOrNeg == '2') {
        re = new RegExp("^[-]?[0-9]\\d{" + startWhole + "," + endWhole + "}$");
    } else {
        re = new RegExp("^[+-]?[0-9]\\d{" + startWhole + "," + endWhole + "}$");
    }
    dd = (re.test(value));
    return dd;

}


/**
 * @func isToday
 * @desc 是否是今天
 * @param {string} str - 参数str yyyy-MM-dd 或者 yyyy-MM-dd HH:mm 或者 yyyy-MM-dd HH:mm:ss
 */

function isToday(str) {
    var d = new Date(str.replace(/-/g, "/"));
    var todaysDate = new Date();
    return d.setHours(0, 0, 0, 0) === todaysDate.setHours(0, 0, 0, 0);
}
/**
 * @func
 * @desc 是否是未来
 * @param {string} str - 参数str yyyy-MM-dd 或者 yyyy-MM-dd HH:mm 或者 yyyy-MM-dd HH:mm:ss
 */
function isFuture(str) {
    var d = new Date(str.replace(/-/g, "/"));
    var todaysDate = new Date();
    return d >= todaysDate;
}

/**
 * @func
 * @desc 是否时间在一定范围内，向前推几天到今天
 * @param {string} timeStr - 输入的时间
 * @param {int} offsetDay - 当前时间向前推的天数
 */
function isTimeInRange(timeStr, offsetDay) {
    var now = new Date();
    var minDate = new Date(now.getTime() - offsetDay * 24 * 3600 * 1000);
    minDate.setHours(0, 0, 0, 0);
    var inputDate = new Date(timeStr.replace(/-/g, "/"));
    return inputDate >= minDate;
}



/**
 * @func
 * @desc 提示用户模态框,只有一个按钮.
 * @param header {string} - 模态框的title
 * @param body {string} - 模态框的内容, 默认内容是"提示信息"
 * @param buttonContent {string} - 按钮上的内容.默认内容是"我已了解"
 * @param fn - 参数是函数,按钮触发事件
 */
function alertModal(header, body, buttonContent, fn) {
  if(header == null || header == undefined || header == '') {
    header = "提示信息";
  }

  var title = "<div class='widget-header'><h4 class='smaller'><i class='ace-icon fa fa-exclamation-triangle red'></i> ";
  title += header;
  title += "</h4></div>";

  if (body != null && body != undefined && body != '') {
    $('#confirm-body').html(body);
  } else {
    $('#confirm-body').html("请注意提示");
  }

  var buttonHtml = "我已了解";
  if (buttonContent != null && buttonContent != undefined && buttonContent != '') {
    buttonHtml = buttonContent;
  }
  $("#confirm-modal").removeClass('hide').dialog({
    resizable: false,
    width: '320',
    modal: true,
    title: title,
    title_html: true,
    buttons: [
      {
        html: buttonHtml,
        "class": "btn btn-primary btn-minier",
        click: function () {
          $(this).dialog("close");

          if(fn != null && fn != undefined && fn != '') {
            fn();
          }
        }
      }
    ]
  });
}

/**
 * @func
 * @desc 确定模态框,点击确定会触发一个事件, 点击取消,关闭模态框.
 * @param header {string} - 模态框的title, 不可为空
 * @param body {string} - 模态框的内容, 默认内容是"Are you sure"
 * @param fn - 入参为一个函数,即触发事件
 */
function confirmModal(header, body, fn, confirmText, cancelText) {

    if (header == null || header == undefined || header == '') {
        header = "确认信息";
    }

    var title = "<div class='widget-header'><h4 class='smaller'><i class='ace-icon fa fa-exclamation-triangle red'></i> ";
    title += header;
    title += "</h4></div>";

    if (body != null && body != undefined && body != '') {
        $('#confirm-body').html(body);
    } else {
        $('#confirm-body').html("Are you sure?");
    }

    if (confirmText == null || confirmText == undefined || confirmText == '') {
        confirmText = "确定";
    }

    if (cancelText == null || cancelText == undefined || cancelText == '') {
        cancelText = "取消";
    }


    $("#confirm-modal").removeClass('hide').dialog({
        resizable: false,
        width: '320',
        modal: true,
        title: title,
        title_html: true,
        buttons: [
            {
                html: confirmText,
                "class": "btn btn-primary btn-minier",
                click: function () {
                    $(this).dialog("close");
                    fn();
                }
            },
            {
                html: cancelText,
                "class": "btn btn-minier",
                click: function () {
                    $(this).dialog("close");
                }
            }
        ]
    });
}


function tabChange(obj, pid) {
    $.each($(obj).parent().find(".quake-tab-target"), function (index, oneObj) {
        $(oneObj).removeClass("quake-active");
    });
    $.each($(obj).parent().parent().find(".quake-tab-pane"), function (index, oneObj) {
        $(oneObj).removeClass("quake-active");
    });
    $(obj).addClass("quake-active");
    $(obj).parent().parent().find(".quake-tab-pane").eq(pid).addClass("quake-active");
}

/**
 *判断参数是否为空
 * @param param
 */
function checkParamBlank(param){
    if (param == undefined || param == null || $.trim(param) == ''){
        return false;
    }
    return true;
}


/**
 * @func
 * @desc 获取场景类型的描述
 * @param stressType {int} - 压测类型
 */

function getSceneTypeDesc(stressType) {
    if (stressType === 1) {
        return 'http日志回放';
    } else if (stressType === 2) {
        return 'http组合日志回放';
    } else if (stressType === 3) {
        return 'pigeon日志回放';
    } else if (stressType === 4) {
        return 'mtthrift日志回放';
    } else if (stressType === 5) {
        return 'http自定义流量';
    } else if (stressType === 6) {
        return 'rpc自定义流量'; // 废弃
    } else if (stressType === 7) {
        return 'rpc自定义流量';
    } else if (stressType === 8) {
        return 'mapi流量'; // 废弃
    } else if (stressType === 9) {
        return '引流压测';
    } else {
        return '';
    }
}

/**
 * @func
 * @desc 去掉字符串中间的特殊字符:\n,\r,\t
 * @param str {string} - 字符串
 */
function removeSpecialSign(str){
    return str.replace(/[\n\t\r]/g,"");
}