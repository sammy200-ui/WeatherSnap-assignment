package com.weathersnap.app.di;

import com.weathersnap.app.data.remote.OpenMeteoApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class NetworkModule_ProvideOpenMeteoApiFactory implements Factory<OpenMeteoApi> {
  private final Provider<OkHttpClient> clientProvider;

  public NetworkModule_ProvideOpenMeteoApiFactory(Provider<OkHttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public OpenMeteoApi get() {
    return provideOpenMeteoApi(clientProvider.get());
  }

  public static NetworkModule_ProvideOpenMeteoApiFactory create(
      Provider<OkHttpClient> clientProvider) {
    return new NetworkModule_ProvideOpenMeteoApiFactory(clientProvider);
  }

  public static OpenMeteoApi provideOpenMeteoApi(OkHttpClient client) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOpenMeteoApi(client));
  }
}
