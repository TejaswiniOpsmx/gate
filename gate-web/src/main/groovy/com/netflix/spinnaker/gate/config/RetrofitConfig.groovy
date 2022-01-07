/*
 * Copyright 2016 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.gate.config

import com.netflix.spinnaker.config.OkHttp3ClientConfiguration
import com.opsmx.spinnaker.gate.interceptors.RetrofitInterceptor
import groovy.transform.Canonical
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

import java.util.concurrent.TimeUnit

@Canonical
@Configuration
@ConfigurationProperties(prefix = "retrofit")
class RetrofitConfig {

  Long connectTimeout
  Long readTimeout
  Long callTimeout
  Long writeTimeout
  Boolean retryOnConnectionFailure

  @Autowired
  RetrofitInterceptor retrofitInterceptor

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  OkHttpClient okHttpClient(OkHttp3ClientConfiguration okHttpClientConfig) {

    return okHttpClientConfig.create()
      .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
      .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
      .callTimeout(callTimeout, TimeUnit.MILLISECONDS)
      .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
      .retryOnConnectionFailure(retryOnConnectionFailure)
      .addInterceptor(retrofitInterceptor)
      .build()
  }

}
