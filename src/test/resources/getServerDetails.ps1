## This scripts check the server Avrg CPU and Memory utlization along with C drive disk utilization.
##############################################################################  
param(
  # ... other parameters defined here
  [parameter(Mandatory=$true)]
  [string] $serverList
)

#$ServerListFile = "$contentFolderPath\serverList.txt" 
$ServerList = $serverList 
$Result = @()  
ForEach($computername in $ServerList)  
{ 
 
$AVGProc = Get-WmiObject -computername $computername win32_processor |  
Measure-Object -property LoadPercentage -Average | Select Average 
$OS = gwmi -Class win32_operatingsystem -computername $computername | 
Select-Object @{Name = "MemoryUsage"; Expression = {“{0:N2}” -f ((($_.TotalVisibleMemorySize - $_.FreePhysicalMemory)*100)/ $_.TotalVisibleMemorySize) }} 
$vol = Get-WmiObject -Class win32_Volume -ComputerName $computername -Filter "DriveLetter = 'C:'" | 
Select-object @{Name = "C PercentFree"; Expression = {“{0:N2}” -f  (($_.FreeSpace / $_.Capacity)*100) } } 
   
$result += [PSCustomObject] @{  
        ServerName = "$computername" 
        CPULoad = "$($AVGProc.Average)%" 
        MemLoad = "$($OS.MemoryUsage)%" 
        CDrive = "$($vol.'C PercentFree')%" 
    } 
}  

$result 
