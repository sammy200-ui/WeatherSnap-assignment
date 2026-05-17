package com.weathersnap.app.ui.viewmodel;

import android.content.Context;
import androidx.lifecycle.SavedStateHandle;
import com.weathersnap.app.data.local.ReportDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class CreateReportViewModel_Factory implements Factory<CreateReportViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<ReportDao> reportDaoProvider;

  private final Provider<Context> contextProvider;

  public CreateReportViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ReportDao> reportDaoProvider, Provider<Context> contextProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.reportDaoProvider = reportDaoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public CreateReportViewModel get() {
    return newInstance(savedStateHandleProvider.get(), reportDaoProvider.get(), contextProvider.get());
  }

  public static CreateReportViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider, Provider<ReportDao> reportDaoProvider,
      Provider<Context> contextProvider) {
    return new CreateReportViewModel_Factory(savedStateHandleProvider, reportDaoProvider, contextProvider);
  }

  public static CreateReportViewModel newInstance(SavedStateHandle savedStateHandle,
      ReportDao reportDao, Context context) {
    return new CreateReportViewModel(savedStateHandle, reportDao, context);
  }
}
