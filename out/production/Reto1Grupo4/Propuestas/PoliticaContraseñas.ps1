net accounts /lockoutthreshold:3
net accounts /lockoutduration:120
net accounts /lockoutwindow:60
net accounts /maxpwage:30
net accounts /minpwage:2
net accounts /UNIQUEPW:5
net accounts /minpwlen:8
secedit /export /cfg c:\secpol.cfg
(gc C:\secpol.cfg).replace("PasswordComplexity = 0", "PasswordComplexity = 1") |
Out-File C:\secpol.cfg
secedit /configure /db c:\windows\security\local.sdb /cfg c:\secpol.cfg /areas
SECURITYPOLICY
rm -force c:\secpol.cfg -confirm:$false
