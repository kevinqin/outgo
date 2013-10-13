(ns outgo.controllers.og
  (:use [compojure.core :only (defroutes GET POST)])
  (:require [clojure.string :as str]
            [ring.util.response :as ring]
            [outgo.views.og :as view]
            [outgo.models.og :as model]))

(defn index []
  (view/index (model/all)))

(defn create [kevin jessica]
  (do
    (model/create kevin)
    (model/create jessica))
  (ring/redirect "/"))

(defroutes routes
  (GET "/" [] (index))
  (POST "/" [kevin jessica] (create kevin jessica)))
