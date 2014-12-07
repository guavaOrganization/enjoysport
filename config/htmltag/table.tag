<table id="${id}">
<%// 将标签提的内容引进来%>
${tagBody}
<% 
// 默认机制下，全局变量都将传给html tag对应的模板文件，这个跟include一样。当然，这机制也可以改变，对于标签来说，通常是作为一个组件存在，也不一定需要完全传送所有全局变量，
// 而只传送（request,session,这样变量），因此需要重新继承org.beetl.ext.tag.HTMLTagSupportWrapper.并重载callHtmlTag方法。并注册为htmltag标签
%>
我是全局变量${Company}
</table>