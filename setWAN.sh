# You need a wan.config file with the following content (uncommented) :
#PROJECT_DIR=.
#YOUR_IP=<ip here>
#TCPPORT=<port here>
source wan.config

cd $PROJECT_DIR/out/artifacts/App_jar/distWAN
shopt -s globstar
for f in **
do
	[ -f $f ] && grep -q localhost $f && mv $f $f.tmp && sed s/localhost/$YOUR_IP/g $f.tmp >$f && rm $f.tmp && echo $f cleaned.
done

echo
echo AZUL can now be accessed at $YOUR_IP:$TCPPORT/index.html
python3 -m http.server $TCPPORT
