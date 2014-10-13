#!/bin/sh
CONTENT="$@"
if [ -z "$CONTENT" ]; then
	CONTENT="YOLO"
fi
curl -X POST localhost:62001/display --no-keepalive -d "<html><body><h1>$CONTENT</h1></body></html>"
