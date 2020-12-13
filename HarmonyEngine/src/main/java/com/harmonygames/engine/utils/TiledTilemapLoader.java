package com.harmonygames.engine.utils;

import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.graphics.Tilemap;
import com.harmonygames.engine.graphics.Tileset;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class TiledTilemapLoader {

    private final File tileMapLocation;

    public TiledTilemapLoader(File tileMapLocation) {
        this.tileMapLocation = tileMapLocation;
    }

    public Tilemap[] load() {
        int tileWidth, tileHeight;
        int numCols, numRows;

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(tileMapLocation);

            document.getDocumentElement().normalize();

            Element rootElement = document.getDocumentElement();

            numCols = Integer.parseInt(rootElement.getAttribute("width"));
            numRows = Integer.parseInt(rootElement.getAttribute("height"));

            tileWidth = Integer.parseInt(rootElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(rootElement.getAttribute("tileheight"));

            NodeList rootChildren = rootElement.getChildNodes();

            ArrayList<Tileset> tilesets = new ArrayList<>();
            ArrayList<Tilemap> tilemaps = new ArrayList<>();

            for(int i = 0; i < rootChildren.getLength(); i++) {
                Node childNode = rootChildren.item(i);
                if(childNode.getNodeType() != Node.ELEMENT_NODE) continue;

                Element childElement = (Element) childNode;

                // Load the tilesets
                if(childElement.getTagName().equals("tileset")) {
                    int startID = Integer.parseInt(childElement.getAttribute("firstgid"));
                    int tilesetTileWidth = Integer.parseInt(childElement.getAttribute("tilewidth"));
                    int tilesetTileHeight = Integer.parseInt(childElement.getAttribute("tileheight"));

                    NodeList tilesetChildren = childElement.getChildNodes();
                    Element imageElement = null;

                    for(int j = 0; j < tilesetChildren.getLength(); j++) {
                        Node tilesetChildNode = tilesetChildren.item(j);
                        if(tilesetChildNode.getNodeType() != Node.ELEMENT_NODE) continue;

                        if(((Element) tilesetChildNode).getTagName().equals("image")) {
                            imageElement = (Element) tilesetChildNode;
                            break;
                        }
                    }

                    if(imageElement == null) {
                        System.err.println("[Harmony Engine (TiledTileMapLoader)]: Could not identify the image in the tileset data");
                        System.exit(-1);
                    }

                    int imageWidth = Integer.parseInt(imageElement.getAttribute("width"));
                    int imageHeight = Integer.parseInt(imageElement.getAttribute("height"));

                    String spriteSheetName = imageElement.getAttribute("source").replaceAll("\\.\\./", "");
                    spriteSheetName = "assets/" + spriteSheetName;

                    SpriteSheet spriteSheet = Assets.addSpriteSheet(spriteSheetName, imageWidth / tilesetTileWidth,
                            imageHeight / tilesetTileHeight);
                    tilesets.add(new Tileset(spriteSheet, startID));
                } else if(childElement.getTagName().equals("layer")) {
                    String typeString = childElement.getAttribute("name");
                    String layerData = null;

                    NodeList layerChildren = childElement.getChildNodes();

                    for(int j = 0; j < layerChildren.getLength(); j++) {
                        Node layerChildNode = layerChildren.item(j);
                        if(layerChildNode.getNodeType() != Node.ELEMENT_NODE) continue;

                        if(((Element) layerChildNode).getTagName().equals("data")) {
                            layerData = layerChildNode.getTextContent();
                        }
                    }

                    if(layerData == null) {
                        System.err.println("[Harmony Engine (TiledTileMapLoader)]: Could not identify the map data data");
                        System.exit(-1);
                    }

                    tilemaps.add(new Tilemap(layerData, Tilemap.Type.valueOf(typeString.toUpperCase()), tileWidth, tileHeight, numCols, numRows));
                }
            }

            for(Tilemap tilemap : tilemaps) {
                tilemap.load(tilesets);
            }

            return tilemaps.toArray(new Tilemap[0]);

        } catch (Exception e) {
            System.err.println("[Harmony Engine (TiledTilemapLoader)]: Could not load tile map " + tileMapLocation.getPath());
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }

}
