function PortScanner (){
	this.scan = function(callback, target, timeout,data){
		var timeout = (timeout == null) ? 100 : timeout;
		var img = new Image();
		img.onerror = function() {
			if (!img) {
				return;
			}
			img = undefined;
			callback(data,1);
		};
		img.onload = img.onerror;
		img.src = 'http://' + target;

		setTimeout(function() {
			if (!img) {
				return;
			}
			img = undefined;
			callback(data,0);
		}, timeout);
	};
	this.scanPort = function(callback, host, port, timeout,data) {
		this.scan(callback,host + ':' + port, timeout,data)
	};
};
