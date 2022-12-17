$file_groups=Import-Csv -Path departamento.csv 
foreach ($group in $file_groups) { 
  New-LocalGroup -Name $group.nombre -Description $group.descripcion
}

$file_users=Import-Csv -Path empleado.csv 
foreach ($user in $file_users) { 
  $clave=ConvertTo-SecureString $user.contraseña -AsPlainText -Force
  New-LocalUser $user.cuenta -Password $clave -FullName $user.nombre -PasswordNeverExpires $false -AccountNeverExpires 
  
  net user $user.cuenta /logonpasswordchg:yes


  #Añadimos la cuenta de usuario en el grupo de Usuarios del sistema

  $Groups = ("usuarios",$user.departamento)

  foreach ($Group in $Groups){
    Add-LocalGroupMember -Group $Group -Member $user.cuenta
  
  }
}
