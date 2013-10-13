(ns outgo.models.migration
  (:require [clojure.java.jdbc :as sql]))

(defn create-users []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/create-table :users
                      [:id :serial "PRIMARY KEY"]
                      [:email :text "DEFAULT NULL"]
                      [:nick :text "DEFAULT NULL"]
                      [:avatar :text "DEFAULT NULL"]
                      [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])))

(defn create-outgos []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/create-table :outgos
                      [:id :serial "PRIMARY KEY"]
                      [:user_id :int "NOT NULL"]
                      [:breakfast :int "DEFAULT 0"]
                      [:breakfast_note :text "DEFAULT NULL"]
                      [:lunch :int "DEFAULT 0"]
                      [:lunch_note :text "DEFAULT NULL"]
                      [:dinner :int "DEFAULT 0"]
                      [:dinner_note :text "DEFAULT NULL"]
                      [:fruit :int "DEFAULT 0"]
                      [:fruit_note :text "DEFAULT NULL"]
                      [:drink :int "DEFAULT 0"]
                      [:drink_note :text "DEFAULT NULL"]
                      [:individual :int "DEFAULT 0"]
                      [:individual_note :text "DEFAULT NULL"]
                      [:common :int "DEFAULT 0"]
                      [:common_note :text "DEFAULT NULL"]
                      [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])))

(defn drop-table []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/drop-table :users)
    (sql/drop-table :outgos)))

(defn -main []
  (print "Creating database structure...") (flush)
  (create-users)
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/insert-record :users
                     {
                      :email "defkevinend@gmail.com"
                      :nick "kevin"
                      :avatar "kevin.jpg"
                      :created_at (java.sql.Timestamp. (System/currentTimeMillis))})
    (sql/insert-record :users
                     {
                      :email "jessicaflyfly@gmail.com"
                      :nick "jessica"
                      :avatar "jessica.jpg"
                      :created_at (java.sql.Timestamp. (System/currentTimeMillis))}))

  (create-outgos)
  (println " done"))
