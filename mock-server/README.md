# Getting Started

## Guides
The following guides illustrate how to use some features concretely:

### 富友mock说明
1. 修改配置中心cfp-channel
```
fuiou.api.url=http://172.16.1.15:8088/mock-server/fuiou/xml 或 fuiou.api.url=http://139.196.75.96:8088/mock-server/fuiou/xml
fuiou.api.payroll.partnerId=FY000001
fuiou.api.md5Key=e112dc63a91d420ba23c4e70ab33351f
```
2. 重启cfp-channel
```
service cfp-channel restart
```

### 稠州银行mock说明
1. 修改配置中心cfp-cbex
```
czcb.url=http://172.16.1.15:8088/mock-server/czcb 或 czcb.url=http://139.196.75.96:8088/mock-server/czcb
czcb.channelId=ipl
czcb.client_private_key=MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQClEJN8MU4wqNort2mis+jU047UPZcTbv4ZzmrpenLqeCU2fuBhBsGWDHwMpz4DONIOdcH09ffYBjtf7QfBXwQe6sjKHl5vq2bdOqF/xOXCmJ2ViAMjo4kaPNdC2MEEaG2T1jZTGU6jp3J4BZCtD5qVe86wxBs34bNJra60G/WOo5GNWcXDstf3Es9o7OZ0rLWBVD7d3UPFpdbrRXTzY4GzfZ/lC/6gd/j/rxUgmSXuGCkX8orPn12W/MzIfUWbWmyeWbRpHVoKzntf+Coq5p3Z/6XJFiKoYBDKZDzDnHqHsSxj7iKKiJydLBOrrd0x2fZ0En908w6ZVwjGcDSvNZy1AgMBAAECggEBAJkoFUDR2vQJaE3R5CFEf5AiME+8ShaNERbO0aKLrF7kVdsHxJgilbLtKJjxAPgqW7VxDCOHqoz101fBbkW9LOym+uGXZhaFWm+BPGJ6RpnV07nwNsF1tvvyYeWzivzVDH907nkSbUYqU2sL9t6tMMjs1K5td7fVIu8FSanym1jZ7O9AcTZF51vgQcipOpYtmKgHuMzQVzA/TRubXWwG8LrSVIroQXiKkQe+9yPHoxE8vyZu/oDTNsQNJG6rkSnasj18TdbtQNwxMamfR94ZuJF/VrzpUnCyhMuHIniD4sni6Xf/QDxW6aWtiEmb3b4h8DDHLLWWJUU1pKrF6JIy9M0CgYEAz+TJ8H8l73098ioCK+nf6+FXTRt+UXxl7duauh6nJ3I2cx4Dna+qvbADjaxzne09ixbHl/0ceSpM5Qd9eKgGZWP2pdcUjpVQiAl2PyFufYzlP27nGJcBgC7M0qYknsbAoH7v0MKUZ/PrONzDalLjyxmiT/3K25l+xYao83sLM8sCgYEAy0KvUBJkpWm/QIUUP/pBoUQkql+3Jen17I9dKYdcREnpi43UVH4oTmssYL2ReUx13TpmmmX0t5eZW/XtFpjGGFySwgmjJtmQvFlUD9E7GuLmeVAIjhyDX8szmQPQ1Vwa9J3BL7zAMUHOjUHNrT/qwUPRHt77dg/IqUuhiWc1YX8CgYEAhA6S8D/0OwLVfKSOqJJxeQa1S/ew79YTcVpUGPIrv/Te/ZUNOvdBgCj0uOPYtXLj6xxXQQa5NYZXPgzZ+PDSz0kGfiX3mvLiKufHwyZbZ0k+vmKWTZpui5wu44hCLtGVvt2jPHq9hqL8wiwJvdpVdMgdwK44U/JXAuYdID0Cyq0CgYEAmUr3kLnucdnoxS4YcY+5g3gy9tMs+0kRkZElv7Y4XcAohHk4k2h52Xp2g5c0aXjCw0/SJcuS3gtVO8ejQRGDBMh8Xh6KjzgyMfpkrXEs1EAfdYpoiVTzt4E2rsXz75WBNs7q7ae6guuQbXQdO/YihBMzWcbtesK4oQVLFKdj2N0CgYEAoB3fYqQsZiVkMsxGjo3uyXlb3vvbSC8nu9VC/qYdg4DB5Uew5rsPQpsWRs4mJ4o76O00omg76fXt35EI87r2eIffVutA73yQ8DvtEy4TKhRR+fPV3gG03H0aO7PRY3Sda7RrlsyHNfnuiswhrm1Gtr+l0hbJXmVMomcN9NgYJCI=
czcb.server_public_key=MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2Rr5J6loDpwOQJhI/4hVTOk+JZdvFwG913keXOW0kmxeh/wUlUG++RqAHjMQqdV/CV32XPsh8N6XnPTK/wBvB4sCgcThNnpLcXN24Tbmx/svnCk+KRwCD5MJBx5PEMro7bTLlEKQZWwKb/cnu1L8KUeL4dNCpj6/lwfxcNaJytqfuItCYJukXC92bJ8eRWoX2efFCE0ICNCWAvgsfhgsbl6dlKQE8EXtBA7v+RHcWtr9kB8avZORSKTEpF/qqDd9vt0K6cKgVLXnX/COqHv0SwM72BK+LoJ3rEP8BXVj8FLhOIVf0C4UVVqkwejiRpZ8F+HCWq/ckcs+h67iKcwopwIDAQAB
```
2. 重启cfp-cbex
```
service cfp-cbex restart
```

### 上海银行mock说明
1. 生成公私钥证书，并存放到测试服务器/opt/cfp/bosc目录下  
***生成 .p12文件，命令行模式下运行以下命令，密码填写password，然后一路回车，最后确认信息填写是***
```
keytool -genkey -alias privateKey -keyalg RSA -storetype PKCS12 -keystore privateKey.p12
```
***生成 .cer文件***
```
keytool -export -alias privateKey -keystore privateKey.p12 -storetype PKCS12 -storepass password -rfc -file publicKey.cer
```
***由于代码中写死了私钥文件名称为private.pfx，所以修改privateKey.p12文件为private.pfx***
```
ren .\privateKey.p12 private.pfx（windows命令）
mv ./privateKey.p12 private.pfx（linux命令）
```
***由于代码中写死了公钥文件名称为bankofshanghai.cer，所以修改publicKey.cer文件为ankofshanghai.cer***
```
ren .\publicKey.cer bankofshanghai.cer（windows命令）
mv ./publicKey.cer bankofshanghai.cer（linux命令）
```
2. 修改配置中心cfp-cbex
```
bosc.url.2.0=http://172.16.1.15:8088/mock-server/bosc 或 bosc.url.2.0=http://139.196.75.96:8088/mock-server/bosc
bosc.coop.private.key.pwd=password
```
3. 重启cfp-cbex
```
service cfp-cbex restart
```

### AE卡组织mock说明
1. 登录poss，修改AE通道（81002001）的通道前置地址为：
```
http://172.16.1.15:8088/mock-server/ae 或 http://139.196.75.96:8088/mock-server/ae
```