PROJECT_DIR=.
YOUR_IP=192.168.0.10
TCPPORT=9958

shopt -s globstar
for f in $PROJECT_DIR/src/main/java/View/** $PROJECT_DIR/out/**
do
	[ -f $f ] && grep -q localhost $f && mv $f $f.tmp && sed s/localhost/$YOUR_IP/g $f.tmp >$f && rm $f.tmp && echo $f cleaned.
done

cd $PROJECT_DIR/out/artifacts/App_jar/dist
sed 's/<\/body/<script src=js\/autorefresh.js><\/script><\/body/' index.html >indexLAN.html && echo indexLAN.html created.

echo
echo AZUL can now be accessed in LAN at $YOUR_IP:$TCPPORT/indexLAN.html
python3 -m http.server $TCPPORT
