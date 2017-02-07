PORT=${PORT:-80}
CONFIG_DIR=${CONFIG_DIR:-"/etc/nghttpx"}
CONFIG_FILE="$CONFIG_DIR/nghttpx.conf"
WORKERS=${WORKERS:-5}
BACKENDS=`nslookup $BACKEND_DNS 2>&1 | grep Address | awk -F: '{print $2}' | awk '{print $1}' | sort`
BACKENDS_FILE="/tmp/backends"
PID_FILE="/tmp/nghttpx.pid"

function validate_env() {
	echo "Validating environment"

	if [ -z $BACKEND_DNS ]; then
		echo "BACKEND_DNS is a empty"
		exit 1
	fi

	echo "PORT=$PORT"
	echo "BACKEND_DNS=$BACKEND_DNS"
	echo "CONFIG_DIR=$CONFIG_DIR"
	echo "WORKERS=$WORKERS"
	echo "Enviorment validation successful"
}

function create_config() {
	rm -f $CONFIG_FILE
	mkdir -p $CONFIG_DIR
	echo "Creating nghttpx configuration"

	echo "frontend=0.0.0.0,$PORT;no-tls" >> $CONFIG_FILE
	echo "workers=$WORKERS" >> $CONFIG_FILE
	echo "pid-file=$PID_FILE" >> $CONFIG_FILE

	for backend in $BACKENDS
    do
        echo "backend=$backend,$PORT;;no-tls;proto=h2" >> $CONFIG_FILE
    done

	echo "Wrote nghttpx configuration to $CONFIG_FILE"
	echo "--------------------------------"
	cat $CONFIG_FILE
	echo "--------------------------------"
}

function record_backends() {
	rm -f $BACKENDS_FILE
	echo "Recording backends"

	for backend in $BACKENDS
	do
		echo $backend >> $BACKENDS_FILE
	done

	echo "Wrote backends to $BACKENDS_FILE"
	echo "--------------------------------"
	cat $BACKENDS_FILE
	echo "--------------------------------"
}

function start_nghttpx() {
	nghttpx --conf=$CONFIG_FILE
}

validate_env
create_config && record_backends
refreshBackends.sh &
start_nghttpx