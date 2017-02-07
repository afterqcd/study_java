BACKEND_REFRESH_PERIOD=${BACKEND_REFRESH_PERIOD:=15}
PORT=${PORT:-80}
CONFIG_DIR=${CONFIG_DIR:-"/etc/nghttpx"}
CONFIG_FILE="$CONFIG_DIR/nghttpx.conf"
BACKENDS_FILE="/tmp/backends"
BACKENDS_FILE_TMP="/tmp/backends.tmp"
PID_FILE="/tmp/nghttpx.pid"

function validate_env() {
	if [ ! -f $BACKENDS_FILE ]; then
		echo "$BACKENDS_FILE does not exist"
		exit 1
	fi
}

function refresh() {
	local backends=$(get_backends)

	rm -f $BACKENDS_FILE_TMP
	for backend in $backends
	do
		echo $backend >> $BACKENDS_FILE_TMP
	done

	diff $BACKENDS_FILE $BACKENDS_FILE_TMP >/dev/null || do_refresh
}

function get_backends() {
	nslookup $BACKEND_DNS 2>&1 | grep Address | awk -F: '{print $2}' | awk '{print $1}' | sort
}

function do_refresh() {
	echo "Current backends"
	echo "--------------------------------"
	cat $BACKENDS_FILE_TMP
	echo "--------------------------------"

	local config_file_tmp="/tmp/nghttpx.conf.tmp"
	cat $CONFIG_FILE | grep -v backend > $config_file_tmp

	while IFS='' read -r backend || [[ -n "$backend" ]]
	do
    	echo "backend=$backend,$PORT;;no-tls;proto=h2" >> $config_file_tmp
	done < $BACKENDS_FILE_TMP

	mv $config_file_tmp $CONFIG_FILE
	mv $BACKENDS_FILE_TMP $BACKENDS_FILE

	echo "Refreshed $CONFIG_FILE"
	echo "--------------------------------"
	cat $CONFIG_FILE
	echo "--------------------------------"

	kill -SIGHUP `cat $PID_FILE`
}

function refresh_loop() {
	while true
	do
		sleep $BACKEND_REFRESH_PERIOD
		refresh
	done
}

validate_env && refresh_loop