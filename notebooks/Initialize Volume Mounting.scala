// Databricks notebook source
val configs = Map(
  "fs.azure.account.auth.type" -> "OAuth",
  "fs.azure.account.oauth.provider.type" -> "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider",
  "fs.azure.account.oauth2.client.id" -> "ffe1dc29-d26c-441a-8aec-246ad0cfae93",
  "fs.azure.account.oauth2.client.secret" -> dbutils.secrets.get(scope = "keyvault-scope", key = "appsecret"),
  "fs.azure.account.oauth2.client.endpoint" -> "https://login.microsoftonline.com/67b2955f-1e72-4d33-8902-a48d63492efa/oauth2/token")

// COMMAND ----------

dbutils.fs.mount(
  source = "abfss://casestudydata@subhroneelstorage.dfs.core.windows.net/",
  mountPoint = "/mnt/data",
  extraConfigs = configs)

// COMMAND ----------

val mounts = dbutils.fs.mounts()

for(mount <- mounts) {
  println(mount.source + ", " + mount.mountPoint)
}

// COMMAND ----------

// MAGIC %fs
// MAGIC 
// MAGIC ls /mnt/data

// COMMAND ----------

import com.microsoft.crmsystem.udfs._

// COMMAND ----------

println(CRMSystemUtils.getStatus(true))


println(CRMSystemUtils.getCustomerRegion("Chennai"))

// COMMAND ----------

import com.microsoft.ordersystem.udfs._

println(OrderSystemUtils.getOrderAmount(10,10,10))