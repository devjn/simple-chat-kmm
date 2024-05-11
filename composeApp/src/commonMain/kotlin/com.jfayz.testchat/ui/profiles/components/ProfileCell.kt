package com.jfayz.testchat.ui.profiles.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jfayz.domain.model.Profile

@Composable
fun ProfileCell(
    modifier: Modifier = Modifier,
    profile: Profile
) = Box(
    modifier = modifier
        .fillMaxWidth()
        .height(72.dp)
        .padding(16.dp),
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Rounded.AccountCircle,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(8.dp),
            contentDescription = "Profile Picture"
        )
        Text(
            text = profile.name,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
