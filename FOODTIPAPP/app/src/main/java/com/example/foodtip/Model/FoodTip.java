package com.example.foodtip.Model;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.View.MainActivity;
import com.example.foodtip.View.ViewHolder.ComentarisAdapter;
import com.example.foodtip.View.ViewHolder.OptionInterface.CMD;
import com.example.foodtip.ViewModel.FavoritesViewModel;
import com.example.foodtip.ViewModel.HomePageViewModel;
import com.example.foodtip.ViewModel.SearchViewModel;
import com.example.foodtip.ViewModel.SeeRecipeActivityViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FoodTip {
    private static FoodTip foodTip;
    private User user;
    private final static String TAG = "FOODTIP";
    final static long ONE_MEGABYTE = 1024 * 1024;
    public static FoodTip getInstance(){
        if(foodTip == null){
            foodTip = new FoodTip();
        }
        return foodTip;
    }

    public User getUser() {
        if(user == null){
            getCurrentUser();
        }
        else if(user.getId() != FirebaseAuth.getInstance().getUid().toString()){
            getCurrentUser();
        }
        return user;
    }

    /**
     *
     */
    private void getCurrentUser(){
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("user").document(user_id);

        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        String avatar_uri = task.getResult().get("avatar").toString();
                        user = new UserBuilder()
                                .uid(user_id)
                                .user_name((String) task.getResult().get("user_name"))
                                .acc_name((String) task.getResult().get("acc_name"))
                                .password((String) task.getResult().get("password"))
                                .avatar_uri(avatar_uri)
                                .buildUser();
                    }
                });
    }

    /**
     *  Crear un nou usuari
     * @param activity
     * @param name email
     * @param password contrasenya
     * @param user_name user name
     */
    public void CreatNewUser(@NonNull AppCompatActivity activity, String name, String password, String user_name){
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("user");
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(name,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    User user = new UserBuilder(activity)
                            .uid(uid)
                            .user_name(user_name)
                            .acc_name(name)
                            .password(password)
                            .buildUser();

                    StorageReference storageReference = FirebaseStorage.getInstance()
                            .getReference("avatar")
                            .child(user.getId());
                    storageReference.putBytes(foodTip.BitMapToString(user.getAvatar()))
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Map<String,Object> map = new HashMap<>();
                                            map.put("id",user.getId());
                                            map.put("acc_name",user.getName());
                                            map.put("password",user.getPassword());
                                            map.put("user_name",user.getUser_name());
                                            map.put("avatar",uri.toString());
                                            collectionReference.document(uid).set(map);
                                        }
                                    });
                                }
                            });
                    Toast.makeText(activity,"User Created",Toast.LENGTH_SHORT).show();
                    activity.finish();
                }else{
                    Toast.makeText(activity,"User already exist",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void login_event(@NonNull MainActivity activity, String email, String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener((task)->{
           if(task.isSuccessful()){
               activity.openHomePage();
           }
           else{
               Toast.makeText(activity,"login fail",Toast.LENGTH_SHORT).show();
           }
        });
    }
    public byte[] BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        return b;
    }

    public Recepta createRecepta(String title, String description, ArrayList<SliderData> images, ArrayList<Ingredient> ingredients, ArrayList<Step> steps){
        return new ReceptaBuilder()
                .title(title)
                .description(description)
                .images(images)
                .ingredients(ingredients)
                .steps(steps)
                .buildRecepta();
    }

    public StorageReference getIngredientStorageReference(Recepta recepta){
        return FirebaseStorage.getInstance()
                .getReference("recepta")
                .child(recepta.getId());
    }
    public String GuardarRecepta(Recepta recepta, Map<String,Object> map){
        DocumentReference docuRef = FirebaseFirestore.getInstance()
                .collection("recepta")
                .document(recepta.getId());

        docuRef.set(map);

        return docuRef.getPath();
    }
    public ArrayList<String> UpdateIngredients(Recepta recepta, String uid){
        //Update ingredient
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        //Guarda ID de ingredient
        ArrayList<String> ingredient_id = new ArrayList<>();

        for(Ingredient ingredient:recepta.getIngredients()){
            Map<String,Object> map = new HashMap<>();
            map.put(recepta.getId(),uid);
            DocumentReference documentReference = firestore.collection("ingredient")
                    .document(ingredient.getNom());

            documentReference.update(map).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    documentReference.set(map);
                }
            });
            ingredient_id.add(ingredient.getNom());
        }
        return ingredient_id;
    }

    public void UpdatePictures(Activity activity, Recepta recepta, String documentRef){
        DocumentReference docuRef = FirebaseFirestore.getInstance().document(documentRef);

        for(SliderData sliderData: recepta.getImages()){
            ImageDecoder.Source source = ImageDecoder.createSource(activity.getContentResolver(),Uri.parse(sliderData.getImgUri()));
            try{
                String pictureID = UUID.randomUUID().toString();
                StorageReference storageRef = this.getIngredientStorageReference(recepta).child("images")
                        .child(pictureID);
                storageRef.putBytes(this.BitMapToString(ImageDecoder.decodeBitmap(source)))
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        docuRef.update("bitmaps", FieldValue.arrayUnion(uri.toString()));
                                    }
                                });
                            }
                        });
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void UpdateSteps(Recepta recepta, String documentRef){
        DocumentReference documentReference = FirebaseFirestore.getInstance().document(documentRef);
        for(Step step:recepta.getSteps()){
            if(step.getImages() != null){
                String picture_id = UUID.randomUUID().toString();
                StorageReference storageRef = this.getIngredientStorageReference(recepta).child("images")
                        .child(picture_id);
                storageRef.putBytes(this.BitMapToString(step.getImages())).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Map<String,String> step_map = new HashMap<>();
                                step_map.put("title",step.getTitle());
                                step_map.put("text",step.getText());
                                step_map.put("uri",uri.toString());
                                documentReference.update("steps",FieldValue.arrayUnion(step_map));
                            }
                        });
                    }
                });

            }else{
                Map<String,String> step_map = new HashMap<>();
                step_map.put("title",step.getTitle());
                step_map.put("text",step.getText());
                step_map.put("uri",null);
                documentReference.update("steps",FieldValue.arrayUnion(step_map));
            }
        }
    }

    public void getReceptaInformation(HomePageViewModel viewModel, @CMD int cmd) {
        CollectionReference db = FirebaseFirestore.getInstance().collection("recepta");
        Query query = FirebaseFirestore.getInstance().collection("recepta");
        /**
         * CMD.ADD-> Add more Recepta in list
         * CMD.DELETE-> Refresh List
         */
        switch (cmd){
            case CMD.ADD:
                query.orderBy("likesNum", Query.Direction.DESCENDING).startAfter(viewModel.getSTART()).limit(10).get().addOnCompleteListener(task -> {
                    getRecepta(db,task,viewModel);
                });
                break;
            case CMD.REFRESH:
                query.orderBy("likesNum", Query.Direction.DESCENDING).limit(10).get().addOnCompleteListener(task -> {
                    getRecepta(db,task,viewModel);
                });
                break;
            default:
                break;
        }
    }
    private void getRecepta(CollectionReference db, Task<QuerySnapshot> task, HomePageViewModel viewModel){
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                db.document(document.getId())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                Recepta recepta = new ReceptaBuilder()
                                        .id(document.getId())
                                        .description((String) task.getResult().get("description"))
                                        .title((String) task.getResult().getString("title"))
                                        .ingredients(foodTip.StringArray_To_IngredientArray((ArrayList<String>) task.getResult().get("ingredient")))
                                        .steps(foodTip.MapsArray_To_StepsArray((ArrayList<HashMap<String, Object>>) task.getResult().get("steps")))
                                        .images(foodTip.StringArray_To_SliderDataArray((ArrayList<String>) task.getResult().get("bitmaps")))
                                        .likes((ArrayList<String>) task.getResult().get("likes"))
                                        .comentaris(foodTip.MapArray_To_CometariArray((ArrayList<HashMap<String, Object>>) task.getResult().get("comentaris")))
                                        .buildRecepta();
                                viewModel.add_recepta(recepta);
                            }
                        });
                viewModel.setSTART(document);
            }
        }
    }
    public void getAllRecepta(SearchViewModel searchViewModel){
        CollectionReference db = FirebaseFirestore.getInstance().collection("recepta");
        db.get().addOnCompleteListener(task->{
            if (task.isSuccessful()){
                for(QueryDocumentSnapshot document : task.getResult()){
                    db.document(document.getId())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    Recepta recepta = new ReceptaBuilder()
                                            .id(document.getId())
                                            .description((String)task.getResult().get("description"))
                                            .title((String)task.getResult().getString("title"))
                                            .ingredients(foodTip.StringArray_To_IngredientArray((ArrayList<String>) task.getResult().get("ingredient")))
                                            .steps(foodTip.MapsArray_To_StepsArray((ArrayList<HashMap<String, Object>>) task.getResult().get("steps")))
                                            .images(foodTip.StringArray_To_SliderDataArray((ArrayList<String>) task.getResult().get("bitmaps")))
                                            .likes((ArrayList<String>) task.getResult().get("likes"))
                                            .comentaris(foodTip.MapArray_To_CometariArray((ArrayList<HashMap<String, Object>>) task.getResult().get("comentaris")))
                                            .buildRecepta();
                                    searchViewModel.addRecepta(recepta);
                                }
                            });
                }
            }
        });
    }
    private ArrayList<SliderData> StringArray_To_SliderDataArray(ArrayList<String> input){
        ArrayList<SliderData> output = new ArrayList<>();
        for(String str:input){
            output.add(new SliderData(str));
        }
        return output;
    }

    private ArrayList<Ingredient> StringArray_To_IngredientArray(ArrayList<String> input){
        ArrayList<Ingredient> output = new ArrayList<>();
        for(String str:input){
            output.add(new Ingredient(str));
        }
        return output;
    }

    private ArrayList<Comentari> MapArray_To_CometariArray(ArrayList<HashMap<String,Object>> input){
        ArrayList<Comentari> output = new ArrayList<>();
        if(input != null) {
            for (Map<String,Object> map:input){
                String id = (String) map.get("id");
                String autor = (String) map.get("autor");
                String recepta = (String) map.get("recepta");
                String comentari = (String) map.get("comentari");
                ArrayList<String> liked = map.get("liked") == null?new ArrayList<>():(ArrayList<String>) map.get("liked");
                output.add(new Comentari(id,autor,recepta,comentari,liked));
            }
        }
        return output;
    }

    private ArrayList<Step> MapsArray_To_StepsArray(ArrayList<HashMap<String,Object>> input) {
        ArrayList<Step> output = new ArrayList<>();
        for (Map<String,Object> map:input){
            Bitmap bitmap = null;
            try{
                bitmap = BitmapFactory.decodeStream((InputStream) new URL((String) map.get("uri")).getContent());
            } catch (Exception e){
            }
            Step step = new Step((String) map.get("title"),(String) map.get("text"), bitmap);
            step.setUrl((String) map.get("uri"));
            output.add(step);
        }
        return output;
    }

    public ArrayList<Recepta> getReceptaByTag(ArrayList<String> tags){
        return  null;
    }

    public ArrayList<String> getValidIngredient(SearchViewModel viewModel){
        CollectionReference ref = FirebaseFirestore.getInstance().collection("ingredient");
        ref.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            viewModel.setIngredient(documentSnapshot.getId());
                        }
                    }
                });
        return null;
    }

    public void click_like_recepta(Recepta recepta, @CMD int mode){
        if(mode == CMD.ADD){
            recepta.getLikes().add(user.getId());
            DocumentReference ref1 = FirebaseFirestore.getInstance().collection("recepta").document(recepta.getId());
            ref1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    ref1.update("likes",FieldValue.arrayUnion(user.getId()));
                    ref1.update("likesNum",FieldValue.increment(1));
                }
            });
            DocumentReference ref2 = FirebaseFirestore.getInstance().collection("user").document(user.getId());
            ref2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    ref2.update("liked",FieldValue.arrayUnion(recepta.getId()));
                }
            });


        }else if(mode == CMD.DELETE){
            recepta.getLikes().remove(user.getId());
            DocumentReference ref1 = FirebaseFirestore.getInstance().collection("recepta").document(recepta.getId());
            ref1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    ref1.update("likes",FieldValue.arrayRemove(user.getId()));
                    ref1.update("likesNum",FieldValue.increment(-1));
                }
            });
            DocumentReference ref2 = FirebaseFirestore.getInstance().collection("user").document(user.getId());
            ref2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    ref2.update("liked",FieldValue.arrayRemove(recepta.getId()));
                }
            });
        }

    }

    public void getMisFavoritos(FavoritesViewModel viewModel){
        DocumentReference ref = FirebaseFirestore.getInstance().collection("user").document(user.getId());
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> receptas = (ArrayList<String>) task.getResult().get("liked");
                CollectionReference db = FirebaseFirestore.getInstance().collection("recepta");
                for(String str:receptas){
                    db.document(str)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    Recepta recepta = new ReceptaBuilder()
                                            .id(str)
                                            .description((String)task.getResult().get("description"))
                                            .title((String)task.getResult().getString("title"))
                                            .ingredients(foodTip.StringArray_To_IngredientArray((ArrayList<String>) task.getResult().get("ingredient")))
                                            .steps(foodTip.MapsArray_To_StepsArray((ArrayList<HashMap<String, Object>>) task.getResult().get("steps")))
                                            .images(foodTip.StringArray_To_SliderDataArray((ArrayList<String>) task.getResult().get("bitmaps")))
                                            .likes((ArrayList<String>) task.getResult().get("likes"))
                                            .buildRecepta();

                                    viewModel.add_recepta(recepta);
                                }
                            });
                }
            }
        });

    }

    public void publicar_comentaris(Comentari comentari){
        DocumentReference ref = FirebaseFirestore.getInstance().collection("recepta").document(comentari.getRecepta());
        Map<String,Object> map = new HashMap<>();
        map.put("autor",comentari.getAutor());
        map.put("comentari",comentari.getComment());
        map.put("recepta",comentari.getRecepta());
        map.put("id",comentari.getID());
        ref.update("comentaris",FieldValue.arrayUnion(map));
        ref.update("CommentNum",FieldValue.increment(1));
    }
}
