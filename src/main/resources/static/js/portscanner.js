function PortScanner (){
	this.scanPort = function(callback, target, port, timeout,data) {
		var timeout = (timeout == null) ? 100 : timeout;
		var img = new Image();
		img.onerror = function() {
			if (!img) {
				return;
			}
			img = undefined;
			callback(target, port, 1,data);
		};
		img.onload = img.onerror;
		img.src = 'http://' + target + ':' + port;

		setTimeout(function() {
			if (!img) {
				return;
			}
			img = undefined;
			callback(target, port, 0,data);
		}, timeout);
	};

	this.scanTarget = function(callback, target, ports, timeout,data) {
		for (index = 0; index < ports.length; index++) {
			AttackAPI.PortScanner.scanPort(callback, target, ports[index], timeout,data);
		}
	};
};
