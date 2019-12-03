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

String cadena = fecha

String dia = cadena.substring(0, 2)

String mes = cadena.substring(3, 5)

String año = cadena.substring(6, 10)

String añocorto = cadena.substring(8, 10)

String fechadesde = (((dia + '/') + mes) + '/') + añocorto

String fechaBD = (((año + '-') + mes) + '-') + dia

//Insert variable fecha in FechaDesde
WebUI.executeJavaScript(('$("#vFECHADESDE").val("' + fechadesde) + '");', null)

//Press Tab
WebUI.sendKeys(findTestObject('Object Repository/Virologia/Page_Bienvenida/Page_Bandeja de trabajo de Unidades (1)/input_Fecha desde_vFECHADESDE'), 
    Keys.chord(Keys.TAB))

//Select Estado Todos
WebUI.executeJavaScript('$("#vSOLICITUDESTADO").val("");', null)

//click buton filtro (Lupa)
WebUI.click(findTestObject('Object Repository/Virologia/Page_Bienvenida/Page_Bandeja de trabajo de Unidades (1)/img_Sector_IMAGE1'))

CRUD crud = new CRUD()
String[] resultFC = crud.FiltroFechaVirologia(fechaBD)

boolean filtroF;
int pos = 1
for(int i=0;i<resultFC.length;i++){
	filtroF = true
	String ElemTable = WebUI.executeJavaScript('return $("#span_vSOL_FECHAHORA_000'+pos+'").text();', null)
	ElemTable = ElemTable.substring(0, 10)
	pos = pos + 1
	println ("!!!El valor $i es:" + resultFC[i])
	println ("!!!El Elemento JavaScript $i es:" + ElemTable)
	
	if(!resultFC[i].equals(ElemTable))
		{
		filtroF = false
	}
	println ("El valor de la booleana es:" + filtroF)
}

if (filtroF) {
    println('El filtro Fecha Desde funciona correctamente, Prueba Correcta')
} else {
    throw new Exception('Prueba Incorrecta')
}

WebUI.delay(2)

WebUI.closeBrowser()
