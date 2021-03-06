/**
 * 
 */
package nl.derpt.android.internal.jobs;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicNameValuePair;
import android.content.Context;
import android.util.Log;

/**
 * @author paul_000
 * 
 */
public class login extends Job {
	
	private Job nested;

	/**
	 * @param context
	 * @param session
	 */
	public login(Context context, Manager manager) {
		super(context, manager);
	}
	
	public login(Context context, Manager manager, Job job)
	{
		super(context, manager);
		nested = job;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub

		this.params.add(new BasicNameValuePair("email", this.manager.user));
		this.params.add(new BasicNameValuePair("password", this.manager.pass));

		HttpResponse response = doPost("/login");
		if (response == null)
		{
			return false;
		}

		Log.d("derpt", "Status:" + response.getStatusLine());
		StatusLine st = response.getStatusLine();

		if (st.getStatusCode() == 200) {
			// It is OK.
			Log.d("derpt", "OK");
			return true;
		} else {
			Log.d("derpt", "NOT OK");
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (nested == null)
		{
			this.manager.showProgress(false);
		}
		else
		{
			this.manager.RunJob(nested);
		}
	}
}
