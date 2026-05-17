package com.weathersnap.app.ui.viewmodel;

import com.weathersnap.app.data.local.ReportDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class SavedReportsViewModel_Factory implements Factory<SavedReportsViewModel> {
  private final Provider<ReportDao> reportDaoProvider;

  public SavedReportsViewModel_Factory(Provider<ReportDao> reportDaoProvider) {
    this.reportDaoProvider = reportDaoProvider;
  }

  @Override
  public SavedReportsViewModel get() {
    return newInstance(reportDaoProvider.get());
  }

  public static SavedReportsViewModel_Factory create(Provider<ReportDao> reportDaoProvider) {
    return new SavedReportsViewModel_Factory(reportDaoProvider);
  }

  public static SavedReportsViewModel newInstance(ReportDao reportDao) {
    return new SavedReportsViewModel(reportDao);
  }
}
