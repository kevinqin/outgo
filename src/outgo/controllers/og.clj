(ns outgo.controllers.og
  (:use [compojure.core :only (defroutes GET POST)])
  (:require [clojure.string :as str]
            [ring.util.response :as ring]
            [outgo.views.og :as view]
            [outgo.models.og :as model]))

(defn index []
  (view/index (model/today-outgo)))

(defn histories []
  (view/histories (model/all)))

(defn create [kevin jessica]
  (do
    (model/create kevin)
    (model/create jessica))
  (ring/redirect "/histories"))

(defroutes routes
  (GET "/" [] (index))
  (GET "/histories" [] (histories))
  (POST "/" [kevin jessica] (create kevin jessica)))
