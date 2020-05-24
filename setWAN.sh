# You need a wan.config file with the following content (uncommented) :
#PROJECT_DIR=.
#YOUR_IP=<ip here>
#TCPPORT=<port here>
source wan.config

shopt -s globstar
for f in $PROJECT_DIR/out/artifacts/App_jar/distWAN/**
do
	[ -f $f ] && grep -q localhost $f && mv $f $f.tmp && sed s/localhost/$YOUR_IP/g $f.tmp >$f && rm $f.tmp && echo $f cleaned.
done

cd $PROJECT_DIR/out/artifacts/App_jar/distWAN
echo
echo AZUL can now be accessed at $YOUR_IP:$TCPPORT/index.html
python3 -m http.server $TCPPORT
