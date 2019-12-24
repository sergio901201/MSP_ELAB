import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.sql.Connection as Connection
import java.sql.ResultSet as ResultSet
import java.util.Formatter.DateTime as DateTime
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.InternalData as InternalData
import com.kms.katalon.core.testdata.ExcelData as ExcelData
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.TestObjectProperty as TestObjectProperty
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import net.sf.cglib.core.ClassesKey.Key as Key
import com.database.CRUD as CRUD
import com.database.ContarRowsTable as ContarRowsTable
import com.database.FechaHoy as FechaHoy
import com.database.SQLConnect as SQLConnect
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Date as Date

InternalData ID = findTestData('Data Files/Internal Data Login')

WebUI.callTestCase(findTestCase('Login'), [('User') : ID.getValue(1, 3), ('Password') : ID.getValue(2, 3)], FailureHandling.STOP_ON_FAILURE)

not_run: WebUI.selectOptionByValue(findTestObject('Login/Page_login/select_vMENU'), ID.getValue(1, 13), true)

WebUI.executeJavaScript('$("#vMENU").val("6");', null)

WebUI.click(findTestObject('Login/Page_login/input_Men_BTNCONFIRMAR'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Virologia/Page_Bienvenida/span_Virologia'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Virologia/Page_Bienvenida/td_Bandeja de Trabajo'))

WebUI.delay(2)

String cadenaD = fechaD

String diaD = cadenaD.substring(0, 2)

String mesD = cadenaD.substring(3, 5)

String añoD = cadenaD.substring(6, 10)

String añocortoD = cadenaD.substring(8, 10)

String fechadesde = (((diaD + '/') + mesD) + '/') + añocortoD

String fechaBDD = (((añoD + '-') + mesD) + '-') + diaD

String cadenaH = fechaH

String diaH = cadenaH.substring(0, 2)

String mesH = cadenaH.substring(3, 5)

String añoH = cadenaH.substring(6, 10)

String añocortoH = cadenaH.substring(8, 10)

String fechahasta = (((diaH + '/') + mesH) + '/') + añocortoH

String fechaBDH = (((añoH + '-') + mesH) + '-') + diaH

//Insert variable fecha in FechaDesde
WebUI.executeJavaScript(('$("#vFECHADESDE").val("' + fechadesde) + '");', null)

//Press Tab
WebUI.sendKeys(findTestObject('Object Repository/Virologia/Page_Bienvenida/Page_Bandeja de trabajo de Unidades (1)/input_Fecha desde_vFECHADESDE'), 
    Keys.chord(Keys.TAB))

//Select Fecha Hasta
WebUI.executeJavaScript(('$("#vFECHAHASTA").val("' + fechahasta) + '");', null)

//Select Estado Todos
WebUI.executeJavaScript('$("#vSOLICITUDESTADO").val("");', null)

//Create Data Internal from EstadoEstudio
InternalData IDEstado = findTestData('Data Files/VIROLOGIA/Internal Data Estado')

//Select EstadoEstudio = EnProceso
String Estado = IDEstado.getValue(1, 3)

//Select Estado Todos
WebUI.executeJavaScript('$("#vSOLICITUDESTADO").val("' + Estado + '");', null)
println ("El Sector seleccionado es:" + Estado)

//click buton filtro (Lupa)
WebUI.click(findTestObject('Object Repository/Virologia/Page_Bienvenida/Page_Bandeja de trabajo de Unidades (1)/img_Sector_IMAGE1'))

int longitud = WebUI.executeJavaScript('return $("#SolicitudesContainerTbl tbody tr").length;', null)

String ElemTable

if(longitud > 0){
	WebUI.delay(1)
	ElemTable = WebUI.executeJavaScript('$("#vACC_RESULTADO_0001").click();', null)
	WebUI.delay(2)
}
WebUI.delay(2)

//Click en Comentarios
WebUI.click(findTestObject('Object Repository/Virologia/Page_Ingreso de Resultado/input_  _BUTTON1'))

WebUI.delay(2)

longitud = WebUI.executeJavaScript('return $("#gxp0_ifrm").contents().find("#W0008Grid1ContainerTbl tbody tr").length;', null)
println ("La longitud es de:" + longitud)

String rotulo, rotuloF
if(longitud > 0){
	rotulo = WebUI.executeJavaScript('return $("#gxp0_ifrm").contents().find("#span_W0008INF_ROTULO_0001").text();', null)
	println ("El valor del rótulo es:" + rotulo)
	
	WebUI.executeJavaScript('$("#gxp0_ifrm").contents().find("#W0008vINF_ROTULO").val("'+rotulo+'")', null)
	WebUI.delay(1)
	WebUI.executeJavaScript('$("#gxp0_ifrm").contents().find("#W0008BUTTON1").click();', null)
	WebUI.delay(1)
}

int longitudF = WebUI.executeJavaScript('return $("#gxp0_ifrm").contents().find("#W0008Grid1ContainerTbl tbody tr").length;', null)
boolean filtroF
println ("La longitud luego de aplicar filtro es de:" + longitudF)

if(longitudF > 0){
	rotuloF = WebUI.executeJavaScript('return $("#gxp0_ifrm").contents().find("#span_W0008INF_ROTULO_0001").text();', null)
	println ("El valor del rótulo después de Filtrar es:" + rotuloF)
	WebUI.delay(1)
}

if(longitud == longitudF && !rotulo.equals(rotuloF)){
	filtroF = false
}
else{
	filtroF = true
}

WebUI.delay(1)

if (filtroF) {
    println("El filtro Rótulo funciona correctamente, Prueba Correcta")
} else {
    throw new Exception('Prueba Incorrecta')
}

WebUI.delay(2)

WebUI.closeBrowser()

