<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String jspPath = path+"/WEB-INF/jsp/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
  	<title>盘龙溪漂流</title>
  	<meta charset="utf-8" />
    <meta name="description" content="漂流 盘龙溪漂 湖北漂流 流 湖北旅游 夏季旅游" />
    <meta name="keywords" content="漂流 盘龙溪漂 湖北漂流 流 湖北旅游 夏季旅游" />
    <meta name="author" content="盘龙溪漂流" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    
    <link rel="icon" href="images/favicon.ico" type="image/x-icon" />    
    <link rel="shortcut icon" href="images/PLXlogo.Ico" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="css/wap/style.css">
	<link rel="stylesheet" type="text/css" href="css/wap/index.css">
	<link rel="stylesheet" type="text/css" href="css/pro_pages_style.css">
	
	<script src="js/wap/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="js/wap/Adaptive.js"></script>
    
  <style type="text/css">
  	ol{padding-left: 5px;}
  	ol li{
	  	list-style-type:decimal;
	    list-style-position:inside;
	  	line-height: 2; 
	  	font-size: large;
  	}
  	ul{padding-left: 5px;}
  	ul li{
	  	list-style-type:disc;
	    list-style-position:inside;
	  	line-height: 2; 
	  	font-size: large;
  	}
  </style>
</head>
<body>
	
	<div id="_contain">
	
		<div class="banner">
			<img src="images/wap/index/banner.jpg">
		    <div class="logo-dbs">
		    	<div class="dbs-img">
		        	<img src="images/wap/icon/PLXlogo80px.jpg">
		        </div>
		        <p style="color:red">盘龙溪漂流,安全第一,惊险刺激！</p>
		    </div>
		</div>
		
		
		<div class="favorable" style="width:inherit;border-radius:30px;background:green;margin-top:5px;margin-left:2px;margin-right:2px;">
			<h3 style="text-align: center; margin-bottom: 1px; padding-top: 10px;">安全须知</h3>
			<span>漂流注意事项：</span>
			<ol>
				<li>漂流前请认真阅读漂流注意事项，漂流时请听从安全员的指挥，不听指挥者安全员有权停止其漂流活动。</li>
				<li><p style="color: yellow;">有精神病、高血压、痴呆症、心脏病、身体残疾不健康、饮酒后、怀孕及骨质疏松症的病史严禁漂流，如有隐瞒的导致一切后果自行负责。</p></li>
				<li>最好携带一套干净的衣服和鞋子下船时更换 ，穿着上尽量选择简单、易干的衣服，但不要太薄或色彩太淡，否则万一掉到水里会很尴尬，不得穿拖鞋长裙打赤脚赤膊参加漂流活动。</li>
				<li>漂流时不得携带贵重物品及任何可能由于水浸而受损的物品（如：摄像机、照相机、现金、 首饰、票据、手机、手表等等）请在漂流前做好妥善保管，若有翻船或其它意外事情发生，漂流公司和保险公司不会赔偿游客所遗失的现金和物品。</li>
				<li>漂流者必须将救生衣、安全帽穿戴坚固，不着装者不得上艇，途中不得解下。以免发生溺水、划伤、碰伤等事故，不遵守本规定导致的后果由自己承担责任。</li>
				<li>漂流是具有风险的特种旅游项目。建议游客自行购买旅游意外险！严禁18周岁以下、男55周岁以上、女50周岁以上、年龄不符合规定者不得参加漂流，造成后果自负。</li>
				<li>由于本景区漂流属于比较刺激的漂流，请不要戴眼镜参加漂流活动，以免碰伤鼻梁，建议游客带隐形眼镜或者不带眼睛参加漂流活动。</li>
				<li>漂流中翻船属正常情况，请游客穿好救生衣，不得随便下船游泳，不得远离船体独自行动。即使游泳也应按照船工的意见在平静的水面游，不得远离船体独立行动；若遇翻船，您完全不用慌张，要沉着，因为您穿有救生衣。</li>
				<li>请不要把带尖、刃的利器带上船，以保证游客人身及船体的安全；漂流中请不要用手，脚支撑石头，以免受到伤害。</li>
				<li>漂流工具为自助式皮艇，每条艇限载2人，不准1个人独自漂流。</li>
			</ol>
			<span>漂流注意事项：</span>
			<ol>
				<li>冲滩时请将浆顺艇身侧放在身体与艇之间，两手紧紧抓住漂流艇两边的安全把手，身体要坐正，略前倾，以降低重心</li>
				<li>遇到卡船搁浅的地方。两个人抓紧橡皮艇把手左右摇一摇使橡皮艇能够顺流而下</li>
				<li>漂流时两个人一条橡皮艇，建议男女搭配，两个人体重相差不超过10KG</li>
				<li>漂流过程需要紧紧抓住挺身把手，为了防止我们“纤纤玉手"磨出泡建议我们大家每人都要带副薄手套也避免手背晒黑(针对女生)。</li>
				<li>切勿在冲滩过程中松开安全把手，用手去抹脸上的浪花，以免被抛出艇外</li>
			</ol>
			<span>温馨提示：</span>
			<ul>
				<li>如遇到自然、社会、人为等不可抗拒因素造成无法漂流，如发生洪水、塌方、河道堵塞、气候恶劣的情况下，景区随时有权终止和更改漂流活动。</li>
			</ul>
		</div>
		 
		
	</div>
	
<!-- =====================================footer=============================== -->
<jsp:include page="footer.jsp" flush="true">  
  <jsp:param name="view" value="action"/>
</jsp:include>    
	    
	<script type="text/javascript">
	</script>
</body>
</html>