1.HelloWord simple简单队列
2.work  queue 工作队列  公平分发，轮询分发
3.Publish/Subscribe 发布订阅 
Exchange一共有四种类型：direct、topic、headers 和fanout
fanout:所有队列都发
direct:根据
topic:
channel.exchangeDeclare(exchange, type);
