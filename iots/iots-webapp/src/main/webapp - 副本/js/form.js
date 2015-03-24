// JavaScript Document

function Dsy()
{
	this.Items = {};
}
Dsy.prototype.add = function(id,iArray)
{
	this.Items[id] = iArray;
}
Dsy.prototype.Exists = function(id)
{
	if(typeof(this.Items[id]) == "undefined") return false;
	return true;
}
function change(v){
	var str="0";
	for(i=0;i<v;i++){str+=("_"+(document.getElementById(s[i]).selectedIndex-1));};
	var ss=document.getElementById(s[v]);
	with(ss){
	  length = 0;
	  options[0]=new Option(opt0[v],opt0[v]);
	  if(v && document.getElementById(s[v-1]).selectedIndex>0 || !v)
	  {
	   if(dsy.Exists(str)){
		ar = dsy.Items[str];
		for(i=0;i<ar.length;i++)options[length]=new Option(ar[i],ar[i]);
		if(v)options[1].selected = true;
	   }
	  }
	  if(++v<s.length){change(v);
	  }
	}
}
var dsy = new Dsy();
dsy.add("0",["河北","内蒙古"]);
dsy.add("0_0",["承德","唐山"]);
dsy.add("0_0_0",["秦皇岛市","青龙满族自治县"]);
dsy.add("0_0_0_0",["河北省秦皇岛市公安局"]);
dsy.add("0_0_0_0_0",["河北省秦皇岛市公安局办公室","河北省秦皇岛市公安局刑警支队"]);
dsy.add("0_0_0_0_0_0",["河北省秦皇岛市公安局办公室综合科","河北省秦皇岛市公安局办公室秘书科","河北省秦皇岛市公安局办公室调研科"]);
dsy.add("0_0_0_0_0_1",["河北省秦皇岛市公安局刑警支队综合处","河北省秦皇岛市公安局刑警支队法制处"]);
dsy.add("0_0_0_1",["河北省青龙满族自治县公安局"]);
dsy.add("0_0_0_1_0",["河北省青龙满族自治县公安局交警大队","河北省青龙满族自治县公安局政治处"]);
dsy.add("0_0_0_1_0_0",["河北省青龙满族自治县公安局交警大队综合科","河北省青龙满族自治县公安局交警大队一中队","河北省青龙满族自治县公安局交警大队车辆管理站"]);
dsy.add("0_0_0_1_0_1",["河北省青龙满族自治县公安局政治处组织干部科","河北省青龙满族自治县公安局政治处宣传教育科"]);
dsy.add("0_0_1",["唐山市","遵化市"]);
dsy.add("0_1",["包头","呼和浩特"]);
dsy.add("0_1_0",["包头市","土默特右旗"]);
dsy.add("0_1_1",["呼和浩特市","武川县"]);
