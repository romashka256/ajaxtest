package com.ajaxtest.utils

import com.core.repository.repository.DataSourceError


fun createDataSourceError(throwable: Throwable) = DataSourceError(-1, false, throwable)
