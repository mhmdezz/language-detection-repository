package com.ezz.ld.business;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ezz.ld.LangDetectorApplication;
import com.ezz.ld.domain.LanguageProfile;
import com.ezz.ld.exceptions.LangDetectorException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LangDetectorApplication.class)
public class LangDetectFacadeIT {

	@Autowired
	LangDetectFacade facade;
	
	@Test
	public void testLearnLanguage(){
		LanguageProfile language;
		boolean exceptionOccurred = false;
		String englishText = "Philosopher Edward S. Casey (1996) describes: The very word culture meant place tilled in Middle English, and the same word goes back to Latin colere, to inhabit, care for, till, worship and cultus, A cult, especially a religious one. To be cultural, to have a culture, is to inhabit a place sufficiently intensive to cultivate it — to be responsible for it, to respond to it, to attend to it caringly.";
		String spanishText = "Las primeras décadas como país independiente fueron conflictivas: todavía cuando no habían acabado las guerras de la Independencia surgieron fuertes conflictos ante la hegemonía de los unitarios, a la cual se opuso el federalismo propugnado por el oriental José Gervasio Artigas ―también héroe de la Independencia argentina― quien llegó a constituir una liga de provincias argentinas federales. Las luchas entre unitarios y federales condujeron a la Argentina a una larga serie de sangrientas guerras civiles entre facciones, caudillos, y provincias (1820-1861); también, la ocupación lusobrasileña de la Provincia Oriental desencadenó una Guerra contra el Imperio del Brasil (1825-1828). Respecto del territorio, en 1826, mientras Argentina se encontraba en guerra contra el Brasil la provincia de Tarija fue incorporada a Bolivia y ―como resultado de la Convención Preliminar de Paz, que intentaba poner fin a la guerra con el Brasil― en 1828 la Provincia Oriental fue declarada independiente, adoptando el nombre de Estado Oriental del Uruguay.53 Entre 1820 y 1852 —excepto un breve intervalo entre 1825 y 1827— el país careció de un gobierno nacional, asumiendo las provincias la plenitud del gobierno en el ámbito de sus respectivos territorios. Sin embargo, en 1829 Juan Manuel de Rosas, porteño de tendencia federal, asumió el gobierno de la provincia de Buenos Aires con «facultades extraordinarias», conservando la representación externa de las demás provincias.54 En esa etapa, Rosas gobernó con mano de hierro la Confederación Argentina, y combatió los levantamientos de los unitarios. Afrontó con éxito un un bloqueo naval francés, y luego, en 1845 el bloqueo naval de una alianza anglo-francesa. También mantuvo conflictos bélicos contra la Confederación Perú-Boliviana, y contra el llamado Gobierno de la Defensa de Montevideo (colorado) al apoyar la Confederación Argentina a los nacionales uruguayos.";
		String deutchText = "Der Anthropologe Edward Tylor bestimmt Kultur 1871 („Primitive Culture“) unter Aufnahme der darwinschen und gibt so eine erste an den Erkenntnissen der Naturwissenschaft orientierte Definition: „Cultur oder Civilisation im weitesten ethnographischen Sinn ist jener Inbegriff von Wissen, Glauben, Kunst, Moral, Gesetz, Sitte und alle übrigen Fähigkeiten und Gewohnheiten, welche der Mensch als Glied der Gesellschaft sich angeeignet hat.";
		
		try {
			language = facade.detectLanguage(deutchText);
			Assert.assertTrue(language.getName().equalsIgnoreCase("DEUTCH"));
			
			language = facade.detectLanguage(englishText);
			Assert.assertTrue(language.getName().equalsIgnoreCase("ENGLISH"));
			
			language = facade.detectLanguage(spanishText);
			Assert.assertTrue(language.getName().equalsIgnoreCase("SPANISH"));
			
		} catch (LangDetectorException e) {
			exceptionOccurred = true;
			e.printStackTrace();
		}
		
		Assert.assertFalse(exceptionOccurred);
	}
	
	
}
