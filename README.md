# UHCenario 👾

<b>UHCenario</b> ist ein, auf der <b>Spigot</b>-Plattform basierendes, <b>Opensource</b>-Szenarien-Plugin für Spielmodie wie bspw. UHC, ODV und Meetups.

Dieses Plugin lässt sich grundlegend bis auf die letzten Items in jeweiligen Inventaren konfigurieren.
Somit haben Administatoren die Möglichkeit, das Plugin auf deren Designideen einzurichten und die Szenarien so einzustellen,
sodass Sie den Spielern den größtmöglichen Spielspaß garantieren können.

**Wichtig:** Bitte beachtet, dass bei der gradle.build <b>immer</b> eine Spigot-Repository hinterlegt ist, welche die CraftBukkit-API mit beinhaltet!

## Erstellen eigener Szenarien

### Erstelle eine neue Szenario-Klasse:
- Sollten Events in Verwendung kommen, implementiere einfach die Listener-Klasse.
  Diese wird auch direkt vom Plugin registriert.
- Muss eine Aktion beim Aktivieren/Deaktivieren eines Szenarios ausgeführt werden, implementiere die Executable-Klasse,
  die vom Szenario-Plugin bereitgestellt wird.
- Gebe in den Konstruktor _[super(...)]_, einen Szenario-Key ein, der für die Identifikation des Szenarios, benötigt wird.
```java
public class ExampleSzenario extends Scenario implements Listener, Executable {
    
    public ExampleSzenario() {
        super("exampleScenario");
    }

    @Override
    public void onEnable() {
        System.out.println("Scenario is enabled!");
    }

    @Override
    public void onDisable() {
        System.out.println("Scenario is disabled!");
    }
}
```
### Registrierung des Szenarios:
- Gebe folgendes in die Hauptmethode deines Plugins ein.
- Wie schon oben beschrieben, muss kein Event für das Szenario registriert werden, da dies schon vom Plugin übernommen wird.
```java
UHCenario.getInstance().registerScenario(new ExampleSzenario());
```

Andernfalls kann man auch ein komplettes Package registrieren lassen.<br>
Wichtig ist: Es sollten in diesen Package dann auch nur Szenarien-Klassen liegen und nichts anderes. 
```java
UHCenario.getInstance().registerScenarios("de.exampleproject.scenarios");
```


### Eintrag in die Scenarios-Yaml Datei:
```yaml
exampleScenario:
    name: '§eExampleScenario'
    material: STONE
    id: 0
    enabled: false
    votable: false
    lore:
      - '§8§m------------'
      - '§7Thats a example scenario.'
  ...
```
