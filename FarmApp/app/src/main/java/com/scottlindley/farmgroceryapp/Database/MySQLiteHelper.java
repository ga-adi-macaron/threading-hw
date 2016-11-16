package com.scottlindley.farmgroceryapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.scottlindley.farmgroceryapp.CustomObjects.Farm;
import com.scottlindley.farmgroceryapp.CustomObjects.Food;
import com.scottlindley.farmgroceryapp.CustomObjects.Like;
import com.scottlindley.farmgroceryapp.CustomObjects.Order;
import com.scottlindley.farmgroceryapp.CustomObjects.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Lindley on 11/1/2016.
 */

public class MySQLiteHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "FarmApp";
    public static final int VERSION_NUMBER = 1;

    public static final String USER_TABLE_NAME = "Users";
    public static final String LIKES_TABLE_NAME = "Likes";
    public static final String FOODS_TABLE_NAME = "Foods";
    public static final String FARMS_TABLE_NAME = "Farms";
    public static final String ORDERS_TABLE_NAME = "Order_History";
    public static final String CART_TABLE_NAME = "Cart";

    public static final String COL_ID = "ID";
    public static final String COL_NAME = "Name";
    public static final String COL_STATE = "State";
    public static final String COL_STORY = "Story";
    public static final String COL_SPECIALTY = "Specialty";
    public static final String COL_PRICE = "Price";
    public static final String COL_FARM_ID = "FarmID";
    public static final String COL_USER_ID = "UserID";
    public static final String COL_DATE = "Date";
    public static final String COL_FOOD_NAME = "Food_Name";
    public static final String COL_QUANTITY = "Quantity";

    public static final String CREATE_USER_TABLE =
            "CREATE TABLE "+USER_TABLE_NAME+" ("+
                    COL_ID+" INTEGER PRIMARY KEY, "+
                    COL_NAME+" TEXT, "+
                    COL_STATE+" TEXT)";
    public static final String CREATE_LIKE_TABLE =
            "CREATE TABLE "+LIKES_TABLE_NAME+" ("+
                    COL_ID+" INTEGER PRIMARY KEY, "+
                    COL_USER_ID+" INT, "+
                    COL_FARM_ID+" INT)";
    public static final String CREATE_FARMS_TABLE =
            "CREATE TABLE "+FARMS_TABLE_NAME+" ("+
                    COL_FARM_ID+" INTEGER PRIMARY KEY, "+
                    COL_NAME+" TEXT, "+
                    COL_STORY+" TEXT, "+
                    COL_SPECIALTY+" TEXT, "+
                    COL_STATE+" TEXT)";
    public static final String CREATE_FOODS_TABLE =
            "CREATE TABLE "+FOODS_TABLE_NAME+" ("+
                    COL_NAME+" TEXT,"+
                    COL_FARM_ID+" INTEGER,"+
                    COL_PRICE+" REAL, "+
                    "PRIMARY KEY("+COL_NAME+", "+COL_FARM_ID+"))";

    private static MySQLiteHelper sInstance;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
    }

    public static MySQLiteHelper getInstance(Context context){
        if(sInstance==null){
            sInstance = new MySQLiteHelper(context);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_FARMS_TABLE);
        sqLiteDatabase.execSQL(CREATE_FOODS_TABLE);
        sqLiteDatabase.execSQL(CREATE_LIKE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FARMS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FOODS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+LIKES_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ORDERS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+CART_TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

    public List<Farm> getAllFarms(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                FARMS_TABLE_NAME,
                null,null,null,null,null,null);

        List<Farm> allFarms = new ArrayList<>();

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                allFarms.add(new Farm(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_NAME)),
                        cursor.getString(cursor.getColumnIndex(COL_STORY)),
                        cursor.getString(cursor.getColumnIndex(COL_SPECIALTY)),
                        cursor.getString(cursor.getColumnIndex(COL_STATE))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return allFarms;
    }

    public List<Farm> searchFarms(String query){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                FARMS_TABLE_NAME, null,
                COL_NAME+" LIKE ? OR "+COL_STATE+" LIKE ?",
                new String[]{"%"+query+"%","%"+query+"%"}
                ,null,null,null);

        List<Farm> allFarms = new ArrayList<>();

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                allFarms.add(new Farm(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_NAME)),
                        cursor.getString(cursor.getColumnIndex(COL_STORY)),
                        cursor.getString(cursor.getColumnIndex(COL_SPECIALTY)),
                        cursor.getString(cursor.getColumnIndex(COL_STATE))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return allFarms;
    }

    public Farm getFarmByID(int farmID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                FARMS_TABLE_NAME, null,
                COL_ID+" = ?",
                new String[]{Integer.toString(farmID)},
                null,null,null);

        if(cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            String story = cursor.getString(cursor.getColumnIndex(COL_STORY));
            String specialty = cursor.getString(cursor.getColumnIndex(COL_SPECIALTY));
            String state = cursor.getString(cursor.getColumnIndex(COL_STATE));
            Farm farm =  new Farm(id,name,story,specialty,state);
            cursor.close();
            return farm;
        }
        cursor.close();
        return null;
    }

    public List<Food> getFoodByFarm(int farmID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                FOODS_TABLE_NAME,
                new String[]{COL_NAME, COL_PRICE},
                COL_FARM_ID+" = ?",
                new String[]{String.valueOf(farmID)},
                null,null,null);
        Farm farm = getFarmByID(farmID);

        List<Food> foods = new ArrayList<>();

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                foods.add(new Food(
                        cursor.getString(cursor.getColumnIndex(COL_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(COL_PRICE)),
                        farm));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return foods;
    }

    public List<Farm> getFarmsByFood(String foodName){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+FOODS_TABLE_NAME+" INNER JOIN "+FARMS_TABLE_NAME+" ON "+
                FOODS_TABLE_NAME+"."+COL_FARM_ID+" = "+FARMS_TABLE_NAME+"."+COL_ID+" WHERE "+
                FOODS_TABLE_NAME+"."+COL_NAME+" LIKE '%"+foodName+"%'";
        Cursor cursor = db.rawQuery(query, null);

        List<Farm> farms = new ArrayList<>();
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                farms.add(new Farm(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_NAME)),
                        cursor.getString(cursor.getColumnIndex(COL_STORY)),
                        cursor.getString(cursor.getColumnIndex(COL_SPECIALTY)),
                        cursor.getString(cursor.getColumnIndex(COL_STATE))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return farms;
    }

    public List<Like> getLikes(int farmID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                LIKES_TABLE_NAME, null,
                COL_FARM_ID+" = ?",
                new String[]{String.valueOf(farmID)},
                null,null,null);

        List<Like> likes = new ArrayList<>();

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                likes.add(new Like(
                        cursor.getInt(cursor.getColumnIndex(COL_FARM_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_USER_ID))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return likes;
    }

    public User getUserByID(int userID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                USER_TABLE_NAME, null,
                COL_ID+" = ?",
                new String[]{String.valueOf(userID)},
                null,null,null);

        if(cursor.moveToFirst()){
            User user = new User(
                    cursor.getString(cursor.getColumnIndex(COL_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_STATE)));
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }

    public User getLastUser(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                USER_TABLE_NAME, null,null,null,null,null,null);

        if(cursor.moveToLast()){
            return new User(
                    cursor.getString(cursor.getColumnIndex(COL_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_STATE)),
                    cursor.getInt(cursor.getColumnIndex(COL_ID)));
        }
        return null;
    }

    public void insertUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_STATE, user.getState());
        db.insert(USER_TABLE_NAME,null,values);
        db.close();
    }

    public void insertOrder(Order order){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRICE, order.getOrderPrice());
        values.put(COL_DATE, order.getOrderDate());
        values.put(COL_USER_ID, order.getUserID());
        db.insert(ORDERS_TABLE_NAME,null,values);
        db.close();
    }

    public void insertLike(Like like){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FARM_ID, like.getFarmID());
        values.put(COL_USER_ID, like.getUserID());
        db.insert(LIKES_TABLE_NAME,null,values);
        db.close();
    }

    public void insertCartItem(Food food, int userID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, userID);
        values.put(COL_FARM_ID, food.getFarmID());
        values.put(COL_FOOD_NAME, food.getName());
        values.put(COL_QUANTITY, food.getQuantity());
        db.insert(CART_TABLE_NAME,null,values);
        db.close();
    }

    public Food getFoodByNameAndFarm(String foodName, int farmID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                FOODS_TABLE_NAME, null,
                COL_NAME+" = ? AND "+COL_FARM_ID+" = ?",
                new String[]{foodName, String.valueOf(farmID)},
                null,null,null);
        if(cursor.moveToFirst()) {
            Food food = new Food(
                    cursor.getString(cursor.getColumnIndex(COL_NAME)),
                    cursor.getDouble(cursor.getColumnIndex(COL_PRICE)),
                    getFarmByID(cursor.getInt(cursor.getColumnIndex(COL_FARM_ID))));
            cursor.close();
            return food;
        }
        cursor.close();
        return null;
    }

    public List<Food> getCartItemsByUserID(int userID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                CART_TABLE_NAME, null,
                COL_USER_ID+" = ?",
                new String[]{String.valueOf(userID)},
                null,null,null);

        List<Food> items = new ArrayList<>();
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                items.add(new Food(
                        cursor.getString(cursor.getColumnIndex(COL_FOOD_NAME)),
                        getFoodByNameAndFarm(
                                cursor.getString(cursor.getColumnIndex(COL_FOOD_NAME)),
                                cursor.getInt(cursor.getColumnIndex(COL_FARM_ID)))
                                .getPrice(),
                        getFarmByID(cursor.getInt(cursor.getColumnIndex(COL_FARM_ID))),
                        cursor.getInt(cursor.getColumnIndex(COL_QUANTITY))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return items;
    }

    public void deleteCartItemsByUserID(int userID){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(CART_TABLE_NAME, COL_USER_ID+" = ?",
                new String[]{String.valueOf(userID)});
        db.close();
    }

    public void deleteLike(Like like){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(LIKES_TABLE_NAME, COL_FARM_ID+" = ? AND "+COL_USER_ID+" = ?",
                new String[]{String.valueOf(like.getFarmID()),String.valueOf(like.getUserID())});
        db.close();
    }

    public List<Like> getUserLikes(int userID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                LIKES_TABLE_NAME, null,
                COL_USER_ID+" = ?",
                new String[]{String.valueOf(userID)},
                null,null,null);

        List<Like> likes = new ArrayList<>();
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                likes.add(new Like(
                        cursor.getInt(cursor.getColumnIndex(COL_FARM_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_USER_ID))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return likes;
    }

    public Like getLike(int farmID, int userID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                LIKES_TABLE_NAME, null,
                COL_FARM_ID+" = ? AND "+COL_USER_ID+" = ?",
                new String[]{String.valueOf(farmID), String.valueOf(userID)},
                null,null,null);

        if(cursor.moveToFirst()){
                Like like = new Like(
                        cursor.getInt(cursor.getColumnIndex(COL_FARM_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_USER_ID)));
            cursor.close();
            return like;
        }
        cursor.close();
        return null;
    }

    public List<Order> getOrdersByUserID(int userID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                ORDERS_TABLE_NAME, null,
                COL_USER_ID+" = ?",
                new String[]{String.valueOf(userID)},
                null,null,null);

        List<Order> orders = new ArrayList<>();
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                orders.add(new Order(
                        cursor.getDouble(cursor.getColumnIndex(COL_PRICE)),
                        cursor.getString(cursor.getColumnIndex(COL_DATE)),
                        cursor.getInt(cursor.getColumnIndex(COL_USER_ID))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return orders;
    }

    public void upDateUserInfo(int userID, User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_STATE, user.getState());
        db.update(USER_TABLE_NAME, values, COL_ID+" = ?", new String[]{String.valueOf(userID)});
    }



}
