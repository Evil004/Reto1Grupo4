$file_groups=Import-Csv -Path departamentos.csv 
foreach ($group in $file_groups) { 
  New-LocalGroup -Name $group.nombre -Description $group.descripcion
}

$file_users=Import-Csv -Path empleado.csv 
foreach ($user in $file_users) { 
  $clave=ConvertTo-SecureString $user.contraseña -AsPlainText -Force
  New-LocalUser $user.cuenta -Password $clave -FullName $user.nombre_apellidos -PasswordNeverExpires $false -Description $user.descripcion -AccountNeverExpires 
  
  net user $user.cuenta /logonpasswordchg:yes


  #Añadimos la cuenta de usuario en el grupo de Usuarios del sistema

  $Groups = ("usuarios",$user.grupo_departamento)

  foreach ($Group in $Groups){
    Add-LocalGroupMember -Group $Group -Member $user.cuenta
  
  }
}