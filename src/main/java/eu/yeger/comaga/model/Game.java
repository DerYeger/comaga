package eu.yeger.comaga.model;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Game  
{

   public static final String PROPERTY_score = "score";

   private int score;

   public int getScore()
   {
      return score;
   }

   public Game setScore(int value)
   {
      if (value != this.score)
      {
         int oldValue = this.score;
         this.score = value;
         firePropertyChange("score", oldValue, value);
      }
      return this;
   }



   protected PropertyChangeSupport listeners = null;

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (listeners != null)
      {
         listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public boolean addPropertyChangeListener(PropertyChangeListener listener)
   {
      if (listeners == null)
      {
         listeners = new PropertyChangeSupport(this);
      }
      listeners.addPropertyChangeListener(listener);
      return true;
   }

   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      if (listeners == null)
      {
         listeners = new PropertyChangeSupport(this);
      }
      listeners.addPropertyChangeListener(propertyName, listener);
      return true;
   }

   public boolean removePropertyChangeListener(PropertyChangeListener listener)
   {
      if (listeners != null)
      {
         listeners.removePropertyChangeListener(listener);
      }
      return true;
   }

   public boolean removePropertyChangeListener(String propertyName,PropertyChangeListener listener)
   {
      if (listeners != null)
      {
         listeners.removePropertyChangeListener(propertyName, listener);
      }
      return true;
   }



   public void removeYou()
   {
      this.setSelectedField(null);

      this.withoutFields(this.getFields().clone());


   }
















   public static final String PROPERTY_selectedField = "selectedField";

   private Field selectedField = null;

   public Field getSelectedField()
   {
      return this.selectedField;
   }

   public Game setSelectedField(Field value)
   {
      if (this.selectedField != value)
      {
         Field oldValue = this.selectedField;
         if (this.selectedField != null)
         {
            this.selectedField = null;
            oldValue.setSelectedBy(null);
         }
         this.selectedField = value;
         if (value != null)
         {
            value.setSelectedBy(this);
         }
         firePropertyChange("selectedField", oldValue, value);
      }
      return this;
   }


























   public static final String PROPERTY_width = "width";

   private int width;

   public int getWidth()
   {
      return width;
   }

   public Game setWidth(int value)
   {
      if (value != this.width)
      {
         int oldValue = this.width;
         this.width = value;
         firePropertyChange("width", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_height = "height";

   private int height;

   public int getHeight()
   {
      return height;
   }

   public Game setHeight(int value)
   {
      if (value != this.height)
      {
         int oldValue = this.height;
         this.height = value;
         firePropertyChange("height", oldValue, value);
      }
      return this;
   }


   public static final java.util.ArrayList<Field> EMPTY_fields = new java.util.ArrayList<Field>()
   { @Override public boolean add(Field value){ throw new UnsupportedOperationException("No direct add! Use xy.withFields(obj)"); }};


   public static final String PROPERTY_fields = "fields";

   private java.util.ArrayList<Field> fields = null;

   public java.util.ArrayList<Field> getFields()
   {
      if (this.fields == null)
      {
         return EMPTY_fields;
      }

      return this.fields;
   }

   public Game withFields(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withFields(i);
            }
         }
         else if (item instanceof Field)
         {
            if (this.fields == null)
            {
               this.fields = new java.util.ArrayList<Field>();
            }
            if ( ! this.fields.contains(item))
            {
               this.fields.add((Field)item);
               ((Field)item).setGame(this);
               firePropertyChange("fields", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Game withoutFields(Object... value)
   {
      if (this.fields == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutFields(i);
            }
         }
         else if (item instanceof Field)
         {
            if (this.fields.contains(item))
            {
               this.fields.remove((Field)item);
               ((Field)item).setGame(null);
               firePropertyChange("fields", item, null);
            }
         }
      }
      return this;
   }




   public static final String PROPERTY_turnCount = "turnCount";

   private int turnCount = -1;

   public int getTurnCount()
   {
      return turnCount;
   }

   public Game setTurnCount(int value)
   {
      if (value != this.turnCount)
      {
         int oldValue = this.turnCount;
         this.turnCount = value;
         firePropertyChange("turnCount", oldValue, value);
      }
      return this;
   }








}