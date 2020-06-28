package main.utils;


import main.graphics.Material;
import main.graphics.Mesh;
import main.graphics.Vertex;
import main.math.Vector2f;
import main.math.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import org.lwjglx.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private FileUtil() {
    }

    public static String loadAsString(String path){
        StringBuilder result = new StringBuilder();

        System.out.println("Reading file from path: " + path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtil.class.getResourceAsStream(path)))){
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                result.append(line).append("\n");
            }

        } catch (IOException exc)
        {
            System.err.println(path + " file Path Not Found: " + exc);
        }


        return result.toString();
    }

    public static Mesh loadModel(String modelPath, String texturePath)
    {



        System.out.println("Loading Model from: " + modelPath + " with material: " + texturePath);
        AIScene scene = Assimp.aiImportFile(modelPath, Assimp.aiProcess_JoinIdenticalVertices | Assimp.aiProcess_Triangulate);

        if (scene == null){
            System.err.println("Failed to load Obj: " + modelPath);
        }

        AIMesh mesh = AIMesh.create(scene.mMeshes().get(0));
        int vertexCount = mesh.mNumVertices();

        AIVector3D.Buffer vertices = mesh.mVertices();
        AIVector3D.Buffer normals = mesh.mNormals();

        Vertex[] vertexList = new Vertex[vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            System.out.println(i);
            AIVector3D vertex = vertices.get(i);
            Vector3f meshVertex = new Vector3f(vertex.x(), vertex.y(), vertex.z());

            AIVector3D normal = normals.get(i);
            Vector3f meshNormal = new Vector3f(normal.x(), normal.y(), normal.z());

            Vector2f meshTextureCoord = new Vector2f(0, 0);
            if (mesh.mNumUVComponents().get(0) != 0)
            {
                AIVector3D texture = mesh.mTextureCoords(0).get(i);
                meshTextureCoord.setX(texture.x());
                meshTextureCoord.setY(texture.y());
            }

           vertexList[i] = new Vertex(meshVertex, meshNormal, meshTextureCoord);

        }

        int faceCount = mesh.mNumFaces();
        AIFace.Buffer indices = mesh.mFaces();
        int[] indicesList = new int[faceCount * 3];

        for (int i = 0; i < faceCount; i++) {
            AIFace face = indices.get(i);
            indicesList[i * 3 + 0] = face.mIndices().get(0);
            indicesList[i * 3 + 1] = face.mIndices().get(1);
            indicesList[i * 3 + 2] = face.mIndices().get(2);
        }


        int materialCount = scene.mNumMaterials();
        PointerBuffer materialsBuffer = scene.mMaterials();
        ArrayList<Material> materials = new ArrayList<>();
        for (int i = 0; i < materialCount; ++i) {




        }






        return new Mesh(vertexList, indicesList, new Material(texturePath));


    }


}
