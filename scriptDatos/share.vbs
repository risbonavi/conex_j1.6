strComputer = "." 
Set objWMIService = GetObject("winmgmts:\\" & strComputer & "\root\CIMV2") 
Set colItems = objWMIService.ExecQuery("SELECT * FROM Win32_Share")


'For Each objItem in colItems 
 'Wscript.Echo "Name: " & objItem.Name &" Caption: " & objItem.Caption & "=" & objItem.Path &" Description: " & objItem.Description
'Next
set fileSys=WScript.CreateObject("Scripting.FileSystemObject")	
set shareFile = fileSys.OpenTextFile("share.txt",2 ,True)
	For Each objItem in colItems 
		shareFile.WriteLine ""& objItem.Name &"," & objItem.Caption & "," & objItem.Path
	Next
shareFile.close
		