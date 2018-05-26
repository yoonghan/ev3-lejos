#for pid in $(ps -ef|grep haproxy|awk {'print $2'})
#  do kill -9 $pid; 
#done
pkill haproxy
