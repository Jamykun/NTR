Créer des graphiques : utilisation de jfreechart------------------------------------------------
Url     : http://codes-sources.commentcamarche.net/source/51950-creer-des-graphiques-utilisation-de-jfreechartAuteur  : cs_Julien39Date    : 01/08/2013
Licence :
=========

Ce document intitulé « Créer des graphiques : utilisation de jfreechart » issu de CommentCaMarche
(codes-sources.commentcamarche.net) est mis à disposition sous les termes de
la licence Creative Commons. Vous pouvez copier, modifier des copies de cette
source, dans les conditions fixées par la licence, tant que cette note
apparaît clairement.

Description :
=============

Cette source donne un exemple d'utilisation de JFreeChart pour cr&eacute;er un h
istogramme. Je n'ai cod&eacute; que l'histogramme, les autres types de graphique
s ont un fonctionnement similaire
<br /><a name='source-exemple'></a><h2> Sourc
e / Exemple : </h2>
<br /><pre class='code' data-mode='basic'>
import java.aw
t.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import jav
a.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import
 javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.Chart
Factory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryP
lot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.rende
rer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset
;

public class Graphique extends JPanel {

	private static final long seria
lVersionUID = 1L;

	/** titre : Le titre du graphique affiché en haut */
	pri
vate String titre;
	/** ordonnee : le nom de l'axe des ordonnées */
	private S
tring ordonnee;
	/** abscisses : le nom de l'axe des abscisses */
	private Str
ing abscisse;
	/** valeurs : les valeurs à afficher, elles sont triées par séri
es et par catégories*/
	private List&lt;Float&gt; valeurs;
	/** series : la li
ste des séries */
	private List&lt;String&gt; series;
	/** categories : la lis
te des categories */
	private List&lt;String&gt; categories;
	/** legende : bo
oleen vrai si on affiche la légende */
	private boolean legende;
	/** couleurF
ond : la couleur du fond */
	private Color couleurFond;
	/** couleurBarres : l
es couleurs appliquées aux barres */
	private Color[] couleursBarres = {Color.c
yan.darker(), 
			Color.red, Color.green, Color.cyan, Color.magenta, 
			Color
.yellow, Color.pink, Color.darkGray, Color.orange};

	/**

<ul>	 <li> Constr
ucteur
</li>	 <li> @param titre : le titre du graphique
</li>	 <li> @param abs
cisse : le nom de l'axe des abscisses
</li>	 <li> @param ordonnee : le nom de l
'axe des ordonnées
</li>	 <li> @param valeurs : les valeurs
</li>	 <li> @param
 fond : la couleur de fond
</li>	 <li> @param listeSeries : les séries
</li>	 
<li> @param listeCategory : les catégories
</li>	 <li> @param legende : vrai si
 on affiche la légende
</li>	 <li>/</li></ul>
	public Graphique(String titre, 
String abscisse, String ordonnee, List&lt;Float&gt; valeurs, Color fond, List&lt
;String&gt; listeSeries, List&lt;String&gt; listeCategory, boolean legende) {
	
	super(new GridLayout(1,0));
		this.titre=titre;
		this.ordonnee=ordonnee;
		
this.abscisse=abscisse;
		this.valeurs=valeurs;
		this.series=listeSeries;
		
this.categories=listeCategory;
		this.legende=legende;
		this.couleurFond=fond
;
		initialiser();
	}

	/**

<ul>	 <li> Initialise le graphique
</li>	 <l
i>/</li></ul>
	private void initialiser(){
		DefaultCategoryDataset dataset = 
new DefaultCategoryDataset();
		int k = 0;
		for ( int j=0; j&lt;categories.si
ze(); j++){
			for (int i=0; i&lt;series.size(); i++){
				dataset.addValue(va
leurs.get(k), series.get(i), categories.get(j));
				k++;
			}

		}
		JFree
Chart chart = ChartFactory.createBarChart(
				titre,   					// chart title
		
		abscisse,					// domain axis label
				ordonnee,   				// range axis label
	
			dataset,    				// data
				PlotOrientation.VERTICAL, 	// orientation
				l
egende,                    // include legend
				true,                     	// 
tooltips
				false                     	// URL
		);

		// definition de la c
ouleur de fond
		chart.setBackgroundPaint(couleurFond);

		CategoryPlot plot 
= (CategoryPlot) chart.getPlot();

		//valeur comprise entre 0 et 1 transparen
ce de la zone graphique
		plot.setBackgroundAlpha(0.9f);

		NumberAxis rangeA
xis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(Number
Axis.createIntegerTickUnits());

		BarRenderer renderer = (BarRenderer) plot.g
etRenderer();
		renderer.setDrawBarOutline(false);

		// pour la couleur des 
barres pour chaque serie

		for (int s=0; s&lt;series.size(); s++){
			Gradie
ntPaint gp0 = new GradientPaint(0.0f, 0.0f, couleursBarres[s],
					0.0f, 0.0f,
 new Color(0, 40, 70));
			renderer.setSeriesPaint(s, gp0);

		}		

		Chart
Panel chartPanel = new ChartPanel(chart);
		chartPanel.setFillZoomRectangle(tru
e);
		chartPanel.setMouseWheelEnabled(true);
		chartPanel.setPreferredSize(new
 Dimension(500, 270));

		add(chartPanel);
	}

	/**

<ul>	 <li> Création 
d'un graphique
</li>	 <li> @param a
</li>	 <li>/</li></ul>
	public static voi
d main(String[] a){
		List&lt;Float&gt; donnees = new ArrayList&lt;Float&gt;();

		List&lt;String&gt; l1 = new ArrayList&lt;String&gt;();
		List&lt;String&gt;
 l2 = new ArrayList&lt;String&gt;();
		l2.add(&quot;0&quot;);
		l1.add(&quot;1
&quot;);
		l1.add(&quot;2&quot;);
		l1.add(&quot;3&quot;);
		l1.add(&quot;4&q
uot;);
		donnees.add(2f);
		donnees.add(3f);
		donnees.add(4f);
		donnees.ad
d(4f);
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_
CLOSE);
		f.setBounds(10,10,500,500);
		Graphique g = new Graphique(&quot;titr
e&quot;, &quot;x&quot;, &quot;y&quot;, donnees, Color.white, l2, l1, true);
		f
.add(g);
		f.setVisible(true);
	}

}
</pre>
