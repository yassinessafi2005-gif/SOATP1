package org.example;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Main {
    public static void main(String[] args) {
        File xmlFile = new File("films.xml");
        File xsdFile = new File("films.xsd");

        try {
            // 1. Valider le fichier XML avec le schéma XSD
            SchemaFactory schemaFactory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = schemaFactory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));

            System.out.println("Le fichier XML est valide selon le XSD.");

            // 2. Charger le document XML
            DocumentBuilderFactory documentFactory =
                    DocumentBuilderFactory.newInstance();

            documentFactory.setFeature(
                    "http://apache.org/xml/features/disallow-doctype-decl",
                    true
            );

            Document document = documentFactory
                    .newDocumentBuilder()
                    .parse(xmlFile);

            document.getDocumentElement().normalize();

            XPath xpath = XPathFactory.newInstance().newXPath();

            // Afficher les titres, genres et notes de tous les films
            NodeList films = (NodeList) xpath.evaluate(
                    "/cinema/films/film",
                    document,
                    XPathConstants.NODESET
            );

            System.out.println("\nFilms :");
            for (int i = 0; i < films.getLength(); i++) {
                String titre = xpath.evaluate("titre", films.item(i));
                String genre = xpath.evaluate("genre", films.item(i));
                String note = xpath.evaluate("note", films.item(i));

                System.out.println("- " + titre
                        + " | Genre : " + genre
                        + " | Note : " + note);
            }

            // Afficher les acteurs principaux
            NodeList acteursPrincipaux = (NodeList) xpath.evaluate(
                    "/cinema/films/film/acteurs/acteur[@principal='true']",
                    document,
                    XPathConstants.NODESET
            );

            System.out.println("\nActeurs principaux :");
            for (int i = 0; i < acteursPrincipaux.getLength(); i++) {
                String nom = xpath.evaluate("nom", acteursPrincipaux.item(i));
                String role = xpath.evaluate("role", acteursPrincipaux.item(i));

                System.out.println("- " + nom + " joue " + role);
            }

            // Afficher toutes les projections
            NodeList projections = (NodeList) xpath.evaluate(
                    "/cinema/films/film/projections/projection",
                    document,
                    XPathConstants.NODESET
            );

            System.out.println("\nProjections :");
            for (int i = 0; i < projections.getLength(); i++) {
                String salle = xpath.evaluate("@salle", projections.item(i));
                String date = xpath.evaluate("date", projections.item(i));
                String heure = xpath.evaluate("heure", projections.item(i));
                String prix = xpath.evaluate("prix", projections.item(i));

                System.out.println("- " + date + " à " + heure
                        + " | " + salle
                        + " | Prix : " + prix);
            }

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}