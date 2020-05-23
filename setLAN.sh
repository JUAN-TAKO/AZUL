PROJECT_DIR=/home/cd/Documents/Java/AZUL
YOUR_IP=192.168.0.10
TCPPORT=9958

shopt -s globstar
for f in $PROJECT_DIR/**
do
	[ -f $f ] && grep -q 192.168.0.10 $f && mv $f $f.tmp && sed s/192.168.0.10/$YOUR_IP/g $f.tmp >$f && rm $f.tmp && echo $f cleaned.
done

cd $PROJECT_DIR/out/artifacts/App_jar/dist
sed 's/<\/body/<script src=js\/autorefresh.js><\/script><\/body/' index.html >indexLAN.html && echo indexLAN.html created.

python3 -m http.server $TCPPORT
