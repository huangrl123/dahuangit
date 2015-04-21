<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="../css/css_style.css" rel="stylesheet" type="text/css">
<!-- IE浏览器下的样式 -->
<!--[if gte IE 6]>
<style type="text/css">
.left_menu_title_1{
	height:25px;
	padding:7px 0px 0px 19px;
	cursor:hand;
	font-size:12px;
	background: url(../image/left5.gif) no-repeat 0px;
}
</style>
<![endif]-->
</head>
<body>
	<script language="JavaScript">
	<!--
		function showitem(id, name) {
			return ("<span><a href='"+id+"' target=_blank>" + name + "</a></span><br>")
		}
		function switchoutlookBar(number) {
			var i = outlookbar.opentitle;
			outlookbar.opentitle = number;
			var id1, id2, id1b, id2b
			if (number != i && outlooksmoothstat == 0) {
				if (number != -1) {
					if (i == -1) {
						id2 = "blankdiv";
						id2b = "blankdiv";
					} else {
						id2 = "outlookdiv" + i;
						id2b = "outlookdivin" + i;
						document.all("outlooktitle" + i).style.border = "1px none navy";
						document.all("outlooktitle" + i).style.color = "#0964C1";
						document.all("outlooktitle" + i).style.textalign = "center";
					}
					id1 = "outlookdiv" + number
					id1b = "outlookdivin" + number
					document.all("outlooktitle" + number).style.border = "1px none white";
					document.all("outlooktitle" + number).style.color = "#0964C1";
					document.all("outlooktitle" + number).style.textalign = "center";
					smoothout(id1, id2, id1b, id2b, 0);
				} else {
					document.all("blankdiv").style.display = "";
					document.all("blankdiv").sryle.height = "100%";
					document.all("outlookdiv" + i).style.display = "none";
					document.all("outlookdiv" + i).style.height = "0%";
					document.all("outlooktitle" + i).style.border = "1px none navy";
					document.all("outlooktitle" + i).style.background = outlookbar.maincolor;
					document.all("outlooktitle" + i).style.color = "#0964C1";
					document.all("outlooktitle" + i).style.textalign = "center";
				}
			}
		}
		function smoothout(id1, id2, id1b, id2b, stat) {
			if (stat == 0) {
				tempinnertext1 = document.all(id1b).innerHTML;
				tempinnertext2 = document.all(id2b).innerHTML;
				document.all(id1b).innerHTML = "";
				document.all(id2b).innerHTML = "";
				outlooksmoothstat = 1;
				document.all(id1b).style.overflow = "hidden";
				document.all(id2b).style.overflow = "hidden";
				document.all(id1).style.height = "0%";
				document.all(id1).style.display = "";
				setTimeout("smoothout('" + id1 + "','" + id2 + "','" + id1b + "','" + id2b + "'," + outlookbar.inc + ")", outlookbar.timedalay);
			} else {
				stat += outlookbar.inc;
				if (stat > 100)
					stat = 100;
				document.all(id1).style.height = stat + "%";
				document.all(id2).style.height = (100 - stat) + "%";
				if (stat < 100)
					setTimeout("smoothout('" + id1 + "','" + id2 + "','" + id1b + "','" + id2b + "'," + stat + ")", outlookbar.timedalay);
				else {
					document.all(id1b).innerHTML = tempinnertext1;
					document.all(id2b).innerHTML = tempinnertext2;
					outlooksmoothstat = 0;
					document.all(id1b).style.overflow = "auto";
					document.all(id2).style.display = "none";
				}
			}
		}
		function getOutLine() {
			outline = "<table "+outlookbar.otherclass+">";
			for (i = 0; i < (outlookbar.titlelist.length); i++) {
				outline += "<tr><td><img src='../image/left1.gif' width='39' height='25'/></td><td name=outlooktitle" + i + " id=outlooktitle" + i
						+ " width='80%' background='../image/left4.gif' class='menu_font'";
				if (i != outlookbar.opentitle) {
					outline += "nowrap align=left style='cursor:hand;background-color:" + outlookbar.maincolor + ";color:#0964C1;border:none'";
				} else {
					outline += "nowrap align=left style='cursor:hand;background-color:" + outlookbar.maincolor + ";color:#0964C1;border:none'";
				}
				outline += outlookbar.titlelist[i].otherclass
				outline += " onclick='switchoutlookBar(" + i + ")'><span>";
				outline += outlookbar.titlelist[i].title + "</span></td><td width='1'><img src='../image/left3.gif' width='1' /></td></tr>";
				outline += "<tr><td name=outlookdiv" + i + " valign=bottom align=left id=outlookdiv" + i + " colspan='3'  align='left' style='width: 98%"
				if (i != outlookbar.opentitle)
					outline += ";display:none;height:0%;";
				else
					outline += ";display:;height:100%;";
				outline += "'><div name=outlookdivin" + i + " id=outlookdivin" + i + " style='overflow:auto;width:100%;height:100%;border-bottom:1px #97BDE4 solid;background-color:#ffffff;'>";
				for (j = 0; j < outlookbar.itemlist[i].length; j++)
					outline += showitem(outlookbar.itemlist[i][j].key, outlookbar.itemlist[i][j].title);
				outline += "</div></td></tr>"
			}
			outline += "</table>"
			return outline
		}
		// 显示菜单
		function show() {
			var outline;
			outline = "<div id=outLookBarDiv name=outLookBarDiv style='width=100%;height:100%'>"
			outline += outlookbar.getOutLine();
			outline += "</div>"
			document.write(outline);
		}
		// 菜单项对象
		function theitem(intitle, instate, inkey) {
			this.state = instate;
			this.otherclass = " nowrap ";
			this.key = inkey;
			this.title = intitle;
		}
		// 添加菜单标题
		function addtitle(intitle) {
			// 为当前菜单创建一个保存菜单项的数组
			outlookbar.itemlist[outlookbar.titlelist.length] = new Array();
			// 添加标题
			outlookbar.titlelist[outlookbar.titlelist.length] = new theitem(intitle, 1, 0);
			return (outlookbar.titlelist.length - 1);
		}
		// 添加菜单项
		function additem(intitle, parentid, inkey) {
			if (parentid >= 0 && parentid <= outlookbar.titlelist.length) {
				// 添加菜单项
				outlookbar.itemlist[parentid][outlookbar.itemlist[parentid].length] = new theitem(intitle, 2, inkey);
				// 添加菜单项的样式
				outlookbar.itemlist[parentid][outlookbar.itemlist[parentid].length - 1].otherclass = " nowrap align=left style='height:5' ";
				return (outlookbar.itemlist[parentid].length - 1);
			} else
				additem = -1;
		}
		// 菜单对象
		function outlook() {
			// 标题列表
			this.titlelist = new Array();
			// 菜单项列表
			this.itemlist = new Array();
			this.divstyle = "style='height:100%;width:100%;overflow:auto' align=center";
			this.otherclass = "border=0 cellspacing='0' cellpadding='0' style='height:100%;width:100%;'valign=middle align=center ";
			this.addtitle = addtitle;
			this.additem = additem;
			this.starttitle = -1;
			this.show = show;
			this.getOutLine = getOutLine;
			this.opentitle = this.starttitle;
			this.reflesh = outreflesh;
			this.timedelay = 50;
			this.inc = 10;
			this.maincolor = "#ffffff"

			// 对象方法
			this.addtitle = addtitle;
			this.additem = additem;
			this.show = show;
			this.getOutLine = getOutLine;
			this.reflesh = outreflesh;
		}
		function outreflesh() {
			document.all("outLookBarDiv").innerHTML = outlookbar.getOutLine();
		}
		// 定位菜单
		function locatefold(foldname) {
			if (foldname == "")
				foldname = outlookbar.titlelist[0].title
			for ( var i = 0; i < outlookbar.titlelist.length; i++) {
				if (foldname == outlookbar.titlelist[i].title) {
					outlookbar.starttitle = i;
					outlookbar.opentitle = i;
				}
			}
		}
		// 显示菜单项
		function showitem(key, name) {
			var t = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
			t += "<tr valign='left'>";
			t += "<td width='16%'></td>";
			t += "<td width='84%' align='left' class='left_menu_title_1'><span><a href='" + key + "' target='page' onClick=\"updateArea('" + name + "')\">" + name + "</a></span></td>";
			t += "</tr></table>";
			return t;
		}
		function updateArea(name) {
			//设备管理
			if (name == "设备查询") {
				parent.document.getElementById("areaSpan").innerHTML = "设备管理 &gt; " + name;
			} else if (name == "设备添加") {
				parent.document.getElementById("areaSpan").innerHTML = "设备管理 &gt; " + name;
			} else if (name == "设备状态查询") {
				parent.document.getElementById("areaSpan").innerHTML = "设备管理 &gt; " + name;
			} else if (name == "设备动作查询") {
				parent.document.getElementById("areaSpan").innerHTML = "设备管理 &gt; " + name;
			} else if(name == "用户查询") {
				parent.document.getElementById("areaSpan").innerHTML = "用户管理 &gt; " + name;
			} else if(name == "用户添加") {
				parent.document.getElementById("areaSpan").innerHTML = "用户管理 &gt; " + name;
			} else if(name == "app下载") {
				parent.document.getElementById("areaSpan").innerHTML = "app管理 &gt; " + name;
			} 
		}
		var outlookbar = new outlook();
		var tempinnertext1, tempinnertext2, outlooksmoothstat
		outlooksmoothstat = 0;

		var t;
		t = outlookbar.addtitle('设备管理')

		outlookbar.additem('设备查询', t, '../perception/queryPerceptionPage')

		outlookbar.additem('设备添加', t, '../perception/addPerceptionPage')
		
		t = outlookbar.addtitle('用户管理')

		outlookbar.additem('用户查询', t, '../perception/queryPerceptionPage')

		outlookbar.additem('用户添加', t, '../perception/addPerceptionPage')
		
		t = outlookbar.addtitle('app管理')

		outlookbar.additem('app下载', t, '../perception/queryPerceptionPage')

	//-->
	</script>

	<table id=mnuList style="WIDTH: 198px; HEIGHT: 100%" cellspacing=0 cellpadding=0 align=center border=0>
		<tr>
			<td id=outLookBarShow name="outLookBarShow" style="HEIGHT: 100%" valign=bottom align=middle><script language="JavaScript">
			<!--
				locatefold("")
				outlookbar.show()
			//-->
			</script></td>
		</tr>
	</table>

</body>
</html>
