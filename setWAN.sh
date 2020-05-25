# You need a wan.config file with the following content (uncommented) :
#PROJECT_DIR=.
#YOUR_IP=<ip here>
#TCPPORT=<port here>
source wan.config

cd $PROJECT_DIR/out/artifacts/App_jar/distWAN
shopt -s globstar
for f in **
do
	[ -f $f ] && grep -q localhost $f && echo ok && mv $f $f.old && sed s/localhost/$YOUR_IP/g $f.old >$f && echo $f changed for network use.
done

echo
echo AZUL can now be accessed at $YOUR_IP:$TCPPORT/index.html
python3 -m http.server $TCPPORT

for f in **
do
	[ -f $f.old ] && rm $f && mv $f.old $f && echo $f reverted.
done
