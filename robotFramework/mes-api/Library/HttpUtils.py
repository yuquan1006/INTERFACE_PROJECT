# -*- coding: utf-8 -*-

import requests 

def post_download(url,filename):
	resp = requests.get(url)
	with open(filename, "wb") as file:
	file.write(resp.content)